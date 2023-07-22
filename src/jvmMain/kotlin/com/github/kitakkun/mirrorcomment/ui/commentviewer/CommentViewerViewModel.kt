package com.github.kitakkun.mirrorcomment.ui.commentviewer

import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.AudioPlayer
import com.github.kitakkun.mirrorcomment.coroutines.DefaultScope
import com.github.kitakkun.mirrorcomment.model.MirrativComment
import com.github.kitakkun.mirrorcomment.preferences.SettingsPropertiesRepository
import com.github.kitakkun.mirrorcomment.service.MirrativCommentRetrieveService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class CommentViewerViewModel(
    private val player: AudioPlayer,
) : CoroutineScope by DefaultScope(), KoinComponent {
    private val mutableUiState = MutableStateFlow(CommentViewerState(rawLiveUrl = ""))
    val uiState = mutableUiState.asStateFlow()

    private var retrieveService: MirrativCommentRetrieveService? = null
    private val settingsPropertiesRepository: SettingsPropertiesRepository by inject()
    private val ktVoxApi: KtVoxApi by inject { parametersOf(settingsPropertiesRepository.getVoiceVoxServerUrl()) }

    private val mutableReadUpCommentFlow = MutableSharedFlow<MirrativComment>()

    fun updateLiveUrl(liveUrl: String) {
        mutableUiState.update {
            it.copy(rawLiveUrl = liveUrl)
        }
    }

    fun startObserveComments() {
        cancel()
        val liveId = uiState.value.rawLiveUrl.split("/").last()
        retrieveService = get { parametersOf(liveId) }
        launch {
            retrieveService?.newCommentsFlow?.collect { newComments ->
                mutableUiState.update { it.copy(comments = it.comments + newComments) }
                newComments.forEach { mutableReadUpCommentFlow.emit(it) }
            }
        }
        launch {
            mutableReadUpCommentFlow.collect {
                val audioQuery =
                    ktVoxApi.createAudioQuery(
                        text = "${it.username} ${it.comment}",
                        speaker = 0
                    ).body() ?: return@collect
                val wave = ktVoxApi.postSynthesis(
                    speaker = 0,
                    audioQuery = audioQuery,
                ).body() ?: return@collect
                player.play(wave.bytes())
            }
        }
    }

    fun dispose() {
        retrieveService?.dispose()
        retrieveService = null
    }

    fun openSettings() {
        TODO("Not yet implemented")
    }
}

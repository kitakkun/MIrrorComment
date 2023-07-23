package com.github.kitakkun.mirrorcomment.ui.commentviewer

import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.AudioPlayer
import com.github.kitakkun.mirrorcomment.coroutines.DefaultScope
import com.github.kitakkun.mirrorcomment.model.MirrativComment
import com.github.kitakkun.mirrorcomment.preferences.SettingsPropertiesRepository
import com.github.kitakkun.mirrorcomment.service.MirrativCommentRetrieveService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
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

    private val mutableVoiceVoxErrorFlow = MutableSharedFlow<Unit>()
    val voiceVoxErrorFlow = mutableVoiceVoxErrorFlow.asSharedFlow()

    private val retrieveService: MirrativCommentRetrieveService by inject()
    private val settingsPropertiesRepository: SettingsPropertiesRepository by inject()

    private var ktVoxApi: KtVoxApi
    private var speakerId: Int = 0
    private var speakingEnabled: Boolean = true

    private val mutableReadUpCommentFlow = MutableSharedFlow<MirrativComment>()

    init {
        val voiceVoxServerUrl = settingsPropertiesRepository.getVoiceVoxServerUrl()
        ktVoxApi = get { parametersOf(voiceVoxServerUrl) }

        launch {
            retrieveService.newCommentsFlow.collect { newComments ->
                mutableUiState.update { it.copy(comments = it.comments + newComments) }
                newComments.forEach { mutableReadUpCommentFlow.emit(it) }
            }
        }

        launch {
            mutableReadUpCommentFlow.collect {
                if (!speakingEnabled) return@collect
                try {
                    val audioQuery = ktVoxApi.createAudioQuery(
                        text = "${it.username} ${it.comment}",
                        speaker = speakerId,
                    ).body() ?: return@collect
                    val wave = ktVoxApi.postSynthesis(
                        speaker = speakerId,
                        audioQuery = audioQuery,
                    ).body() ?: return@collect
                    player.play(wave.bytes())
                } catch (e: Throwable) {
                    mutableVoiceVoxErrorFlow.emit(Unit)
                }
            }
        }
    }

    fun updateLiveUrl(liveUrl: String) {
        mutableUiState.update {
            it.copy(rawLiveUrl = liveUrl)
        }
    }

    fun startObserveComments() {
        val liveId = uiState.value.rawLiveUrl.split("/").last()
        retrieveService.stopCollecting()
        retrieveService.startCollecting(liveId)
    }

    fun applySettingsChanges() {
        val voiceVoxServerUrl = settingsPropertiesRepository.getVoiceVoxServerUrl()
        speakingEnabled = settingsPropertiesRepository.getSpeakingEnabled()
        ktVoxApi = get { parametersOf(voiceVoxServerUrl) }
        launch {
            val speakers = ktVoxApi.getSpeakers().body() ?: return@launch
            speakerId = speakers.indexOfFirst { speaker ->
                speaker.speakerUuid == settingsPropertiesRepository.getSpeakerUUID()
            }.coerceAtLeast(0)
        }
    }
}

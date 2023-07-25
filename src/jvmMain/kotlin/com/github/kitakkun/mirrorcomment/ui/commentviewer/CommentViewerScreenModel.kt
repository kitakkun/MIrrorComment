package com.github.kitakkun.mirrorcomment.ui.commentviewer

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.AudioPlayer
import com.github.kitakkun.mirrorcomment.model.MirrativComment
import com.github.kitakkun.mirrorcomment.preferences.SettingsRepository
import com.github.kitakkun.mirrorcomment.service.MirrativCommentRetrieveService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import java.util.*
import kotlin.jvm.optionals.getOrNull

class CommentViewerScreenModel(
    private val player: AudioPlayer,
) : ScreenModel, KoinComponent {
    private val mutableUiState = MutableStateFlow(CommentViewerState(rawLiveUrl = ""))
    val uiState = mutableUiState.asStateFlow()

    private val mutableSnackbarErrorFlow = MutableSharedFlow<String>()
    val snackBarErrorFlow = mutableSnackbarErrorFlow.asSharedFlow()

    private val retrieveService: MirrativCommentRetrieveService by inject()
    private val settingsRepository: SettingsRepository by inject()

    private var ktVoxApi: Optional<KtVoxApi> = Optional.empty()
    private var speakerId: Int = 0
    private var speakingEnabled: Boolean = true

    private val mutableReadUpCommentFlow = MutableSharedFlow<MirrativComment>()

    init {
        val url = settingsRepository.getVoiceVoxServerUrl()
        ktVoxApi = get { parametersOf(url) }

        coroutineScope.launch {
            retrieveService.newCommentsFlow.collect { newComments ->
                mutableUiState.update { it.copy(comments = it.comments + newComments) }
                newComments.forEach { mutableReadUpCommentFlow.emit(it) }
            }
        }

        coroutineScope.launch(Dispatchers.IO) {
            mutableReadUpCommentFlow.collect {
                if (!speakingEnabled) return@collect
                val api = ktVoxApi.getOrNull() ?: return@collect
                try {
                    val audioQuery = api.createAudioQuery(
                        text = "${it.username} ${it.comment}",
                        speaker = speakerId,
                    ).body() ?: return@collect
                    val wave = api.postSynthesis(
                        speaker = speakerId,
                        audioQuery = audioQuery,
                    ).body() ?: return@collect
                    player.play(wave.bytes())
                } catch (e: Throwable) {
                    withContext(Dispatchers.Main) {
                        mutableSnackbarErrorFlow.emit("VOICEVOX: 音声合成に失敗しました")
                    }
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
        val rawLiveUrl = uiState.value.rawLiveUrl
        val liveId = rawLiveUrl.split("/").last()
        if (!rawLiveUrl.startsWith("https://www.mirrativ.com/live/") || liveId.isEmpty()) {
            coroutineScope.launch {
                mutableSnackbarErrorFlow.emit("配信URLが無効です！")
            }
            return
        }
        retrieveService.stopCollecting()
        retrieveService.startCollecting(liveId)
    }

    fun applySettingsChanges() {
        speakingEnabled = settingsRepository.getSpeakingEnabled()
        val url = settingsRepository.getVoiceVoxServerUrl()
        ktVoxApi = get { parametersOf(url) }
        coroutineScope.launch(Dispatchers.IO) {
            val speakers = ktVoxApi.getOrNull()?.getSpeakers()?.body() ?: return@launch
            speakerId = speakers.indexOfFirst { speaker ->
                speaker.speakerUuid == settingsRepository.getSpeakerUUID()
            }.coerceAtLeast(0)
        }
    }
}

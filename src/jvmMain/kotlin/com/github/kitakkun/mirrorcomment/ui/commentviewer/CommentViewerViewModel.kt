package com.github.kitakkun.mirrorcomment.ui.commentviewer

import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.AudioPlayer
import com.github.kitakkun.mirrorcomment.coroutines.DefaultScope
import com.github.kitakkun.mirrorcomment.model.MirrativComment
import com.github.kitakkun.mirrorcomment.service.MirrativCommentRetrieveService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import javax.sound.sampled.AudioSystem

class CommentViewerViewModel(
    private val player: AudioPlayer,
) : CoroutineScope by DefaultScope(), KoinComponent {
    private val mutableUiState = MutableStateFlow(CommentViewerState(rawLiveUrl = ""))
    val uiState = mutableUiState.asStateFlow()

    private var retrieveService: MirrativCommentRetrieveService? = null
    private val ktVoxApi: KtVoxApi by inject()

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
            retrieveService?.commentFlow?.collect { newComments ->
                // 汚いので簡単に説明
                // コメントが読み込まれるたびに既存のIDが上書きされる
                // そのため、現在ローカルで保持しているコメントの中で最新のコメントを取得し、
                // そのコメントが新しいコメントの中にあるかどうかで分岐する
                val localComments = uiState.value.comments
                val latestComment = localComments.firstOrNull()
                val latestCommentOnNewComments =
                    newComments.find { it.comment == latestComment?.comment && it.username == latestComment.username }
                val addedComments = if (latestCommentOnNewComments != null) {
                    newComments.takeWhile { it.id != latestCommentOnNewComments.id }
                } else {
                    newComments
                }
                // 音声読み上げ
                addedComments.take(5).forEach {
                    mutableReadUpCommentFlow.emit(it)
                }
                mutableUiState.update {
                    it.copy(comments = addedComments + it.comments)
                }
            }
        }
        launch {
            mutableReadUpCommentFlow.collect {
                println("Collect")
                val audioQuery =
                    ktVoxApi.createAudioQuery(text = "${it.username} ${it.comment}", speaker = 0).body()
                        ?: return@collect
                val wave = ktVoxApi.postSynthesis(speaker = 0, audioQuery = audioQuery).body() ?: return@collect
                println("Speak")
                player.play(wave.bytes())
            }
        }
    }

    private suspend fun playAudio(bytes: ByteArray) {
        val audioInputStream = AudioSystem.getAudioInputStream(bytes.inputStream())
        val clip = AudioSystem.getClip().apply {
            open(audioInputStream)
            start()
        }

        // 音声が終わるまで待機
        while (clip.isActive) {
            delay(1000) // 1秒待つ
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

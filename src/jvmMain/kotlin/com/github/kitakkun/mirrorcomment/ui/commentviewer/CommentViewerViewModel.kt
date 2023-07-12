package com.github.kitakkun.mirrorcomment.ui.commentviewer

import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.coroutines.DefaultScope
import com.github.kitakkun.mirrorcomment.model.MirrativComment
import com.github.kitakkun.mirrorcomment.service.MirrativCommentRetrieveService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import java.io.File
import javax.sound.sampled.*
import kotlin.concurrent.thread

class CommentViewerViewModel : CoroutineScope by DefaultScope(), KoinComponent {
    private val mutableUiState = MutableStateFlow(CommentViewerState(rawLiveUrl = ""))
    val uiState = mutableUiState.asStateFlow()

    private var retrieveService: MirrativCommentRetrieveService? = null
    private val ktVoxApi: KtVoxApi by inject()

    private val mutableReadUpCommentFlow = MutableSharedFlow<MirrativComment>()
    private val mutableAudioPlayFlow = MutableSharedFlow<ByteArray>()

    init {
        launch {
            withContext(Dispatchers.IO) {
                mutableAudioPlayFlow.collect {
                    val waveFile = File("temp.wav")
                    waveFile.writeBytes(it)
                    thread(name = "Speak") {
                        try {
                            val audioInputStream = AudioSystem.getAudioInputStream(waveFile)
                            val clip = AudioSystem.getClip()
                            clip.open(audioInputStream)
                            clip.start()
                            Thread.sleep(clip.microsecondLength / 1000)
                            audioInputStream.close()
                            clip.close()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
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
                mutableAudioPlayFlow.emit(wave.bytes())
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

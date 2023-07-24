package com.github.kitakkun.mirrorcomment

import com.github.kitakkun.mirrorcomment.coroutines.IOScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.sound.sampled.AudioSystem

class AudioPlayer : CoroutineScope by IOScope() {
    private val waveByteArrayFlow = MutableSharedFlow<ByteArray>()

    init {
        startCollectAndPlay()
    }

    fun play(waveByteArray: ByteArray) {
        launch {
            waveByteArrayFlow.emit(waveByteArray)
        }
    }

    fun clearPlayQueue() {
        cancel()
        startCollectAndPlay()
    }

    private fun startCollectAndPlay() {
        launch {
            waveByteArrayFlow.collect {
                async {
                    try {
                        val audioInputStream = AudioSystem.getAudioInputStream(it.inputStream())
                        val clip = AudioSystem.getClip()
                        clip.open(audioInputStream)
                        clip.start()
                        delay(clip.microsecondLength / 1000)
                        audioInputStream.close()
                        clip.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.await()
            }
        }
    }
}

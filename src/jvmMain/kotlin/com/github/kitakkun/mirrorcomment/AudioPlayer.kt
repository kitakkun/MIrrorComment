package com.github.kitakkun.mirrorcomment

import com.github.kitakkun.mirrorcomment.coroutines.IOScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.File
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
                val waveFile = File("temp.wav")
                waveFile.writeBytes(it)
                val job = async {
                    try {
                        val audioInputStream = AudioSystem.getAudioInputStream(waveFile)
                        val clip = AudioSystem.getClip()
                        clip.open(audioInputStream)
                        clip.start()
                        delay(clip.microsecondLength / 1000)
                        audioInputStream.close()
                        clip.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                job.await()
            }
        }
    }
}

package com.github.kitakkun.mirrorcomment.preferences

import java.io.File
import java.io.FileReader
import java.net.URL
import java.util.*

class SettingsRepository(
    propertiesFilename: String = "settings.properties"
) {
    private val properties = Properties()
    private val propertiesFile = File(propertiesFilename)

    init {
        if (propertiesFile.exists()) {
            FileReader(propertiesFile).use {
                properties.load(it)
            }
        }
    }

    fun getVoiceVoxServerUrl(): String? {
        return try {
            val rawUrl = properties.getProperty("voiceVoxServerUrl", null)
            URL(rawUrl)
            rawUrl
        } catch (e: Exception) {
            null
        }
    }

    fun setVoiceVoxServerUrl(url: String) {
        properties.setProperty("voiceVoxServerUrl", url)
        properties.store(propertiesFile.outputStream(), null)
    }

    fun getSpeakingEnabled(): Boolean {
        return properties.getProperty("speakingEnabled", "false").toBoolean()
    }

    fun setSpeakingEnabled(enabled: Boolean) {
        properties.setProperty("speakingEnabled", enabled.toString())
        properties.store(propertiesFile.outputStream(), null)
    }

    fun getSpeakerUUID(): String {
        return properties.getProperty("speakerUUID", "")
    }

    fun setSpeakerUUID(uuid: String) {
        properties.setProperty("speakerUUID", uuid)
        properties.store(propertiesFile.outputStream(), null)
    }
}

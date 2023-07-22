package com.github.kitakkun.mirrorcomment.preferences

import java.io.File
import java.io.FileReader
import java.util.*

class SettingsPropertiesRepository {
    private val properties = Properties()
    private val propertiesFile = File("settings.properties")

    init {
        if (propertiesFile.exists()) {
            FileReader(propertiesFile).use {
                properties.load(it)
            }
        }
    }

    fun getVoiceVoxServerUrl(): String {
        return properties.getProperty("voiceVoxServerUrl", "")
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
}

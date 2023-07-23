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

    fun getSpeakerUUID(): String {
        return properties.getProperty("speakerUUID", "")
    }

    fun setSpeakerUUID(uuid: String) {
        properties.setProperty("speakerUUID", uuid)
        properties.store(propertiesFile.outputStream(), null)
    }

    fun getCommentForegroundColor(key: String): String {
        return properties.getProperty("${key}ForegroundColor", "#000000")
    }

    fun setCommentForegroundColor(key: String, color: String) {
        properties.setProperty("${key}ForegroundColor", color)
        properties.store(propertiesFile.outputStream(), null)
    }

    fun getCommentBackgroundColor(key: String): String {
        return properties.getProperty("${key}BackgroundColor", "#FFFFFF")
    }

    fun setCommentBackgroundColor(key: String, color: String) {
        properties.setProperty("${key}BackgroundColor", color)
        properties.store(propertiesFile.outputStream(), null)
    }
}

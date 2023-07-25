package com.github.kitakkun.mirrorcomment.preferences

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class SettingsRepository(
    private val settings: Settings = Settings()
) {
    fun getVoiceVoxServerUrl(): String? {
        return try {
            val rawUrl = settings["voiceVoxServerUrl", ""]
            if (rawUrl.isValidHttpOrHttpsUrl()) {
                rawUrl
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun setVoiceVoxServerUrl(url: String) {
        if (!url.isValidHttpOrHttpsUrl()) return
        settings["voiceVoxServerUrl"] = url
    }

    fun getSpeakingEnabled(): Boolean {
        return settings["speakingEnabled", false]
    }

    fun setSpeakingEnabled(enabled: Boolean) {
        settings["speakingEnabled"] = enabled
    }

    fun getSpeakerUUID(): String {
        return settings["speakerUUID", ""]
    }

    fun setSpeakerUUID(uuid: String) {
        settings["speakerUUID"] = uuid
    }
}

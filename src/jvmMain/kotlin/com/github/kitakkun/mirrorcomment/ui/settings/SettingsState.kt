package com.github.kitakkun.mirrorcomment.ui.settings

import com.github.kitakkun.ktvox.schema.extra.Speaker

data class SettingsState(
    val speakingEnabled: Boolean = false,
    val voiceVoxServerUrl: String = "",
    val checkingVoiceVoxServer: Boolean = false,
    val isVoiceVoxServerRunning: Boolean = false,
    val speakers: List<Speaker> = emptyList(),
    val speakerUUID: String = "",
) {
}

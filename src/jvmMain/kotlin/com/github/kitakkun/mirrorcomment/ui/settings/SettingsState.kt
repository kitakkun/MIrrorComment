package com.github.kitakkun.mirrorcomment.ui.settings

data class SettingsState(
    val speakingEnabled: Boolean = false,
    val voiceVoxServerUrl: String = "",
    val checkingVoiceVoxServer: Boolean = false,
    val isVoiceVoxServerRunning: Boolean = false,
) {
}

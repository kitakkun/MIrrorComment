package com.github.kitakkun.mirrorcomment.ui.settings

data class SettingsState(
    val speakingEnabled: Boolean = false,
    val chromeDriverPath: String = "",
    val voiceVoxServerUrl: String = "",
    val checkingVoiceVoxServer: Boolean = false,
    val isVoiceVoxServerRunning: Boolean = false,
) {
}

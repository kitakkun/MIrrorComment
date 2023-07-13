package com.github.kitakkun.mirrorcomment.ui.settings

data class SettingsState(
    val speakingEnabled: Boolean = false,
    val chromeDriverPath: String = "",
    val voiceVoxServerUrl: String = "",
) {
}

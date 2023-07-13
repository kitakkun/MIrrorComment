package com.github.kitakkun.mirrorcomment.ui.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel {
    private val mutableUiState = MutableStateFlow(SettingsState())
    val uiState = mutableUiState.asStateFlow()

    fun updateSpeakingEnabled(enabled: Boolean) {
        mutableUiState.update {
            it.copy(speakingEnabled = enabled)
        }
    }

    fun updateVoiceVoxServerUrl(url: String) {
        mutableUiState.update {
            it.copy(voiceVoxServerUrl = url)
        }
    }

    fun updateChromeDriverPath(path: String) {
        mutableUiState.update {
            it.copy(chromeDriverPath = path)
        }
    }
}

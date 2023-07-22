package com.github.kitakkun.mirrorcomment.ui.settings

import com.github.kitakkun.mirrorcomment.preferences.SettingsPropertiesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel : KoinComponent {
    private val settingsPropertiesRepository: SettingsPropertiesRepository by inject()

    private val mutableUiState = MutableStateFlow(SettingsState())
    val uiState = mutableUiState.asStateFlow()

    fun fetchSettings() {
        mutableUiState.update {
            it.copy(
                speakingEnabled = settingsPropertiesRepository.getSpeakingEnabled(),
                voiceVoxServerUrl = settingsPropertiesRepository.getVoiceVoxServerUrl(),
                chromeDriverPath = settingsPropertiesRepository.getChromeDriverPath(),
            )
        }
    }

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

    fun applySettings() {
        settingsPropertiesRepository.setSpeakingEnabled(uiState.value.speakingEnabled)
        settingsPropertiesRepository.setVoiceVoxServerUrl(uiState.value.voiceVoxServerUrl)
        settingsPropertiesRepository.setChromeDriverPath(uiState.value.chromeDriverPath)
    }
}

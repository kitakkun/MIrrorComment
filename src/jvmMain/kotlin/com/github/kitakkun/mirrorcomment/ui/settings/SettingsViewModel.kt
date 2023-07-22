package com.github.kitakkun.mirrorcomment.ui.settings

import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.coroutines.DefaultScope
import com.github.kitakkun.mirrorcomment.preferences.SettingsPropertiesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class SettingsViewModel : KoinComponent, CoroutineScope by DefaultScope() {
    private val settingsPropertiesRepository: SettingsPropertiesRepository by inject()

    private val mutableUiState = MutableStateFlow(SettingsState())
    val uiState = mutableUiState.asStateFlow()

    private var voiceVoxServerCheckJob: Job? = null

    private fun checkVoiceVoxServerStatus(url: String) {
        voiceVoxServerCheckJob?.cancel()
        voiceVoxServerCheckJob = async {
            mutableUiState.update { it.copy(checkingVoiceVoxServer = true) }
            val connectionAvailable = try {
                val ktVoxApi = get<KtVoxApi> { parametersOf(url) }
                ktVoxApi.getVersion()
                true
            } catch (e: Exception) {
                false
            }
            mutableUiState.update {
                it.copy(checkingVoiceVoxServer = false, isVoiceVoxServerRunning = connectionAvailable)
            }
        }
    }

    fun fetchSettings() {
        mutableUiState.update {
            it.copy(
                speakingEnabled = settingsPropertiesRepository.getSpeakingEnabled(),
                voiceVoxServerUrl = settingsPropertiesRepository.getVoiceVoxServerUrl(),
            )
        }
        checkVoiceVoxServerStatus(uiState.value.voiceVoxServerUrl)
    }

    fun updateSpeakingEnabled(enabled: Boolean) {
        mutableUiState.update {
            it.copy(speakingEnabled = enabled)
        }
    }

    fun updateVoiceVoxServerUrl(url: String) {
        mutableUiState.update { it.copy(voiceVoxServerUrl = url) }
        checkVoiceVoxServerStatus(url)
    }

    fun applySettings() {
        settingsPropertiesRepository.setSpeakingEnabled(uiState.value.speakingEnabled)
        settingsPropertiesRepository.setVoiceVoxServerUrl(uiState.value.voiceVoxServerUrl)
    }
}


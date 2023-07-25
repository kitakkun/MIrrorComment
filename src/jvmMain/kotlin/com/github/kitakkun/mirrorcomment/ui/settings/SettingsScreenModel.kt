package com.github.kitakkun.mirrorcomment.ui.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.preferences.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import java.util.*
import kotlin.jvm.optionals.getOrNull

class SettingsScreenModel : ScreenModel, KoinComponent {
    private val settingsRepository: SettingsRepository by inject()

    private val mutableUiState = MutableStateFlow(SettingsState())
    val uiState = mutableUiState.asStateFlow()

    private var voiceVoxServerCheckJob: Job? = null

    private fun checkVoiceVoxServerStatus(url: String) {
        voiceVoxServerCheckJob?.cancel()
        mutableUiState.update { it.copy(checkingVoiceVoxServer = true) }
        voiceVoxServerCheckJob = coroutineScope.async(Dispatchers.IO) {
            val ktVoxApi = get<Optional<KtVoxApi>> { parametersOf(url) }.getOrNull()
            if (ktVoxApi == null) {
                withContext(Dispatchers.Main) {
                    mutableUiState.update {
                        it.copy(
                            checkingVoiceVoxServer = false,
                            isVoiceVoxServerRunning = false,
                            speakers = emptyList(),
                        )
                    }
                }
                return@async
            }
            val connectionAvailable = try {
                ktVoxApi.getVersion().isSuccessful
            } catch (e: Exception) {
                false
            }
            val speakers = try {
                ktVoxApi.getSpeakers().body() ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
            withContext(Dispatchers.Main) {
                mutableUiState.update {
                    it.copy(
                        checkingVoiceVoxServer = false,
                        isVoiceVoxServerRunning = connectionAvailable,
                        speakers = speakers,
                    )
                }
            }
        }
    }

    fun fetchSettings() {
        mutableUiState.update {
            it.copy(
                speakingEnabled = settingsRepository.getSpeakingEnabled(),
                voiceVoxServerUrl = settingsRepository.getVoiceVoxServerUrl() ?: "",
                speakerUUID = settingsRepository.getSpeakerUUID(),
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
        settingsRepository.setSpeakingEnabled(uiState.value.speakingEnabled)
        settingsRepository.setVoiceVoxServerUrl(uiState.value.voiceVoxServerUrl)
        settingsRepository.setSpeakerUUID(uiState.value.speakerUUID)
    }

    fun updateSpeaker(speakerUUID: String) {
        mutableUiState.update { it.copy(speakerUUID = speakerUUID) }
    }
}


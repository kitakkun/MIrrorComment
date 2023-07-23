package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.ui.graphics.Color
import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.coroutines.DefaultScope
import com.github.kitakkun.mirrorcomment.ext.toColor
import com.github.kitakkun.mirrorcomment.ext.toHexString
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
            val speakers = try {
                val ktVoxApi = get<KtVoxApi> { parametersOf(url) }
                ktVoxApi.getSpeakers().body() ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
            mutableUiState.update {
                it.copy(
                    checkingVoiceVoxServer = false,
                    isVoiceVoxServerRunning = connectionAvailable,
                    speakers = speakers,
                )
            }
        }
    }

    fun fetchSettings() {
        val joinCommentForegroundColor = settingsPropertiesRepository.getCommentForegroundColor("joinComment").toColor()
        val joinCommentBackgroundColor = settingsPropertiesRepository.getCommentBackgroundColor("joinComment").toColor()
        val giftCommentForegroundColor = settingsPropertiesRepository.getCommentForegroundColor("giftComment").toColor()
        val giftCommentBackgroundColor = settingsPropertiesRepository.getCommentBackgroundColor("giftComment").toColor()
        val botCommentForegroundColor = settingsPropertiesRepository.getCommentForegroundColor("botComment").toColor()
        val botCommentBackgroundColor = settingsPropertiesRepository.getCommentBackgroundColor("botComment").toColor()
        mutableUiState.update {
            it.copy(
                speakingEnabled = settingsPropertiesRepository.getSpeakingEnabled(),
                voiceVoxServerUrl = settingsPropertiesRepository.getVoiceVoxServerUrl(),
                speakerUUID = settingsPropertiesRepository.getSpeakerUUID(),
                joinCommentForegroundColor = joinCommentForegroundColor ?: it.joinCommentForegroundColor,
                joinCommentBackgroundColor = joinCommentBackgroundColor ?: it.joinCommentBackgroundColor,
                giftCommentForegroundColor = giftCommentForegroundColor ?: it.giftCommentForegroundColor,
                giftCommentBackgroundColor = giftCommentBackgroundColor ?: it.giftCommentBackgroundColor,
                botCommentForegroundColor = botCommentForegroundColor ?: it.botCommentForegroundColor,
                botCommentBackgroundColor = botCommentBackgroundColor ?: it.botCommentBackgroundColor,
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
        settingsPropertiesRepository.setSpeakerUUID(uiState.value.speakerUUID)
        settingsPropertiesRepository.setCommentForegroundColor(
            "joinComment",
            uiState.value.joinCommentForegroundColor.toHexString()
        )
        settingsPropertiesRepository.setCommentBackgroundColor(
            "joinComment",
            uiState.value.joinCommentBackgroundColor.toHexString()
        )
        settingsPropertiesRepository.setCommentForegroundColor(
            "giftComment",
            uiState.value.giftCommentForegroundColor.toHexString()
        )
        settingsPropertiesRepository.setCommentBackgroundColor(
            "giftComment",
            uiState.value.giftCommentBackgroundColor.toHexString()
        )
        settingsPropertiesRepository.setCommentForegroundColor(
            "botComment",
            uiState.value.botCommentForegroundColor.toHexString()
        )
        settingsPropertiesRepository.setCommentBackgroundColor(
            "botComment",
            uiState.value.botCommentBackgroundColor.toHexString()
        )
    }

    fun updateSpeaker(speakerUUID: String) {
        mutableUiState.update { it.copy(speakerUUID = speakerUUID) }
    }

    fun showColorPickerDialog(colorPickKey: ColorPickKey) {
        mutableUiState.update {
            it.copy(
                showColorPickerDialog = true,
                colorPickTargetKey = colorPickKey,
            )
        }
    }

    fun closeColorPickerDialog() {
        mutableUiState.update {
            it.copy(
                showColorPickerDialog = false,
                colorPickTargetKey = null,
            )
        }
    }

    fun updateColorAndCloseDialog(color: Color) {
        val colorPickTargetKey = uiState.value.colorPickTargetKey ?: return
        mutableUiState.update {
            when (colorPickTargetKey) {
                ColorPickKey.JOIN_COMMENT_FOREGROUND -> {
                    it.copy(joinCommentForegroundColor = color)
                }

                ColorPickKey.JOIN_COMMENT_BACKGROUND -> {
                    it.copy(joinCommentBackgroundColor = color)
                }

                ColorPickKey.GIFT_COMMENT_FOREGROUND -> {
                    it.copy(giftCommentForegroundColor = color)
                }

                ColorPickKey.GIFT_COMMENT_BACKGROUND -> {
                    it.copy(giftCommentBackgroundColor = color)
                }

                ColorPickKey.BOT_COMMENT_FOREGROUND -> {
                    it.copy(botCommentForegroundColor = color)
                }

                ColorPickKey.BOT_COMMENT_BACKGROUND -> {
                    it.copy(botCommentBackgroundColor = color)
                }
            }
        }
        mutableUiState.update {
            it.copy(
                showColorPickerDialog = false,
                colorPickTargetKey = null,
            )
        }
    }
}


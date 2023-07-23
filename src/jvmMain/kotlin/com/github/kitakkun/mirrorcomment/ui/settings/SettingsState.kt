package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.ui.graphics.Color
import com.github.kitakkun.ktvox.schema.extra.Speaker

data class SettingsState(
    val speakingEnabled: Boolean = false,
    val voiceVoxServerUrl: String = "",
    val checkingVoiceVoxServer: Boolean = false,
    val isVoiceVoxServerRunning: Boolean = false,
    val speakers: List<Speaker> = emptyList(),
    val speakerUUID: String = "",
    val showColorPickerDialog: Boolean = false,
    val colorPickTargetKey: ColorPickKey? = null,
    val joinCommentForegroundColor: Color = Color.Black,
    val joinCommentBackgroundColor: Color = Color.Transparent,
    val giftCommentForegroundColor: Color = Color.Black,
    val giftCommentBackgroundColor: Color = Color.Transparent,
    val botCommentForegroundColor: Color = Color.Black,
    val botCommentBackgroundColor: Color = Color.Transparent,
) {
}

package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun SettingsPage(
    viewModel: SettingsViewModel = remember { SettingsViewModel() },
) {
    val uiState by viewModel.uiState.collectAsState()

    SettingsView(
        uiState = uiState,
        onChangeSpeakingEnabled = viewModel::updateSpeakingEnabled,
        onChangeChromeDriverPath = viewModel::updateChromeDriverPath,
        onChangeVoiceVoxServerUrl = viewModel::updateVoiceVoxServerUrl,
        onClickCancel = viewModel::closeSettingsWithoutSaving,
        onClickApply = viewModel::applySettings,
    )
}

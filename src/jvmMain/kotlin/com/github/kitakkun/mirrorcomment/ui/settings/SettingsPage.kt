package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.LocalNavigator

@Composable
fun SettingsPage(
    viewModel: SettingsViewModel = remember { SettingsViewModel() },
) {
    val uiState by viewModel.uiState.collectAsState()
    val navigator = LocalNavigator.current

    LaunchedEffect(Unit) {
        viewModel.fetchSettings()
    }

    SettingsView(
        uiState = uiState,
        onChangeSpeakingEnabled = viewModel::updateSpeakingEnabled,
        onChangeChromeDriverPath = viewModel::updateChromeDriverPath,
        onChangeVoiceVoxServerUrl = viewModel::updateVoiceVoxServerUrl,
        onClickCancel = {
            navigator?.pop()
        },
        onClickApply = {
            viewModel.applySettings()
            navigator?.pop()
        },
    )
}

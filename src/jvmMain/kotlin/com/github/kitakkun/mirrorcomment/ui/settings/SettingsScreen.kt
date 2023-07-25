package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

object SettingsScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { SettingsScreenModel() }

        LaunchedEffect(Unit) {
            screenModel.fetchSettings()
        }

        val uiState by screenModel.uiState.collectAsState()
        val navigator = LocalNavigator.current

        SettingsView(
            uiState = uiState,
            onChangeSpeakingEnabled = screenModel::updateSpeakingEnabled,
            onChangeVoiceVoxServerUrl = screenModel::updateVoiceVoxServerUrl,
            onClickCancel = {
                navigator?.pop()
            },
            onClickApply = {
                screenModel.applySettings()
                navigator?.pop()
            },
            onSpeakerUpdated = screenModel::updateSpeaker,
        )
    }
}

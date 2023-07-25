package com.github.kitakkun.mirrorcomment.ui.commentviewer

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.github.kitakkun.mirrorcomment.ui.settings.SettingsScreen
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class CommentViewerScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { CommentViewerScreenModel(get()) }

        val uiState by viewModel.uiState.collectAsState()
        val navigator = LocalNavigator.current
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(Unit) {
            viewModel.applySettingsChanges()
            launch {
                viewModel.snackBarErrorFlow.collect { message ->
                    snackbarHostState.showSnackbar(message)
                }
            }
        }

        CommentViewerView(
            uiState = uiState,
            onUpdateLiveUrl = viewModel::updateLiveUrl,
            onClickStart = viewModel::startObserveComments,
            onClickSettings = {
                navigator?.push(SettingsScreen)
            },
            snackbarHostState = snackbarHostState,
        )
    }
}

package com.github.kitakkun.mirrorcomment.ui.commentviewer

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.LocalNavigator
import com.github.kitakkun.mirrorcomment.ui.settings.SettingsScreen
import kotlinx.coroutines.launch

@Composable
fun CommentViewerPage(
    viewModel: CommentViewerViewModel,
) {
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

package com.github.kitakkun.mirrorcomment.ui.commentviewer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import com.github.kitakkun.mirrorcomment.ui.settings.SettingsScreen

@Composable
fun CommentViewerPage(
    viewModel: CommentViewerViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val navigator = LocalNavigator.current

    DisposableEffect(Unit) {
        onDispose { viewModel.dispose() }
    }

    CommentViewerView(
        uiState = uiState,
        onUpdateLiveUrl = viewModel::updateLiveUrl,
        onClickStart = viewModel::startObserveComments,
        onClickSettings = {
            navigator?.push(SettingsScreen)
        }
    )
}

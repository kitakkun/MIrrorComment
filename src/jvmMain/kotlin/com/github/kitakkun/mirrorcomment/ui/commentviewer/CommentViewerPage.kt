package com.github.kitakkun.mirrorcomment.ui.commentviewer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.kitakkun.mirrorcomment.ui.CommentViewerView

@Composable
fun CommentViewerPage(
    viewModel: CommentViewerViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose { viewModel.dispose() }
    }

    CommentViewerView(
        uiState = uiState,
        onUpdateLiveUrl = viewModel::updateLiveUrl,
        onClickStart = viewModel::startObserveComments,
        onClickSettings = viewModel::openSettings,
    )
}

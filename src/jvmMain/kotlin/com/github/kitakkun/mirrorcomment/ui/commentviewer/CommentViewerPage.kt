package com.github.kitakkun.mirrorcomment.ui.commentviewer

import androidx.compose.runtime.*
import com.github.kitakkun.mirrorcomment.ui.CommentViewerView

@Composable
fun CommentViewerPage(
    viewModel: CommentViewerViewModel = remember { CommentViewerViewModel() },
) {
    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose { viewModel.dispose() }
    }

    CommentViewerView(
        uiState = uiState,
        onUpdateLiveUrl = viewModel::updateLiveUrl,
        onClickStart = viewModel::startObserveComments,
    )
}

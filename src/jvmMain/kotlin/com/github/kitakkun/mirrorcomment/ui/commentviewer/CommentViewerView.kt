package com.github.kitakkun.mirrorcomment.ui.commentviewer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.kitakkun.mirrorcomment.ui.TopToolBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentViewerView(
    uiState: CommentViewerState,
    snackbarHostState: SnackbarHostState,
    onUpdateLiveUrl: (String) -> Unit,
    onClickStart: () -> Unit,
    onClickSettings: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopToolBar(
                liveUrl = uiState.rawLiveUrl,
                onLiveUrlChange = onUpdateLiveUrl,
                onClickStart = onClickStart,
                onClickSettings = onClickSettings,
                modifier = Modifier.padding(8.dp)
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.comments.asReversed()) { comment ->
                CommentItemView(
                    avatarUrl = comment.avatarUrl,
                    username = comment.username,
                    comment = comment.comment,
                    modifier = Modifier.animateItemPlacement()
                )
                Divider(modifier = Modifier.height(1.dp))
            }
        }
    }
}

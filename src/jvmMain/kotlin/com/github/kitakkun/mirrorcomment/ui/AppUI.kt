package com.github.kitakkun.mirrorcomment.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.kitakkun.mirrorcomment.ui.commentviewer.CommentViewerState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentViewerView(
    uiState: CommentViewerState,
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
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.comments) { comment ->
                Row(
                    modifier = Modifier.animateItemPlacement(),
                ) {
                    Text(text = comment.username, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = comment.comment)
                }
                Divider(modifier = Modifier.height(1.dp))
            }
        }
    }
}

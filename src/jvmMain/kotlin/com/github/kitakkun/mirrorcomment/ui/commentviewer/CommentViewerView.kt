package com.github.kitakkun.mirrorcomment.ui.commentviewer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.github.kitakkun.mirrorcomment.loadNetworkImage
import com.github.kitakkun.mirrorcomment.ui.TopToolBar

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
                modifier = Modifier.padding(8.dp)
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.comments.asReversed()) { comment ->
                Row(
                    modifier = Modifier
                        .animateItemPlacement()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        bitmap = loadNetworkImage(comment.avatarUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(comment.username)
                        }
                        append(" ")
                        append(comment.comment)
                    })
                }
                Divider(modifier = Modifier.height(1.dp))
            }
        }
    }
}

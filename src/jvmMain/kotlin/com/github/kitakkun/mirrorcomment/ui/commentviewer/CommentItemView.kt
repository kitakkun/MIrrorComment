package com.github.kitakkun.mirrorcomment.ui.commentviewer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

@Composable
fun CommentItemView(
    avatarUrl: String,
    username: String,
    comment: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            bitmap = loadNetworkImage(avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(username)
            }
            append(" ")
            append(comment)
        })
    }
}

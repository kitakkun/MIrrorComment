package com.github.kitakkun.mirrorcomment.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MirrativCommentColorConfigRow(
    label: String,
    foregroundColor: Color,
    backgroundColor: Color,
    onPickForegroundColor: () -> Unit,
    onPickBackgroundColor: () -> Unit,
    onClickPreview: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label)
        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable { onPickForegroundColor() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "文字色:")
            ColorTile(
                color = foregroundColor,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(Modifier.width(16.dp))
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable { onPickBackgroundColor() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "背景色:")
            ColorTile(
                color = backgroundColor,
                modifier = Modifier.size(32.dp)
            )
        }
        IconButton(onClick = onClickPreview) {
            Icon(
                imageVector = Icons.Default.Preview,
                contentDescription = "プレビュー",
            )
        }
    }
}

package com.github.kitakkun.mirrorcomment.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopToolBar(
    liveUrl: String,
    onLiveUrlChange: (String) -> Unit,
    onClickStart: () -> Unit,
    onClickSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = liveUrl,
            onValueChange = onLiveUrlChange,
            label = { Text("ライブ配信のURL") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = onClickStart) {
            Text("取得開始")
        }
        IconButton(onClick = onClickSettings) {
            Icon(Icons.Default.Settings, contentDescription = "設定")
        }
    }
}

package com.github.kitakkun.mirrorcomment.ui.splash

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.kitakkun.mirrorcomment.BuildConfig

@Composable
fun SplashView() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = BuildConfig.APP_NAME,
                    style = MaterialTheme.typography.h2,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "v${BuildConfig.VERSION_NAME}",
                    style = MaterialTheme.typography.h5,
                )
            }
            Spacer(modifier = Modifier.width(32.dp))
            Icon(
                painter = painterResource("icon.svg"),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(180.dp),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "アプリケーションの初期化中です．\nしばらくお待ちください．",
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(32.dp))
        LinearProgressIndicator()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "初回起動時は数分かかる場合があります．",
            style = MaterialTheme.typography.caption,
        )
    }
}

@Preview
@Composable
private fun SplashViewPreview() {
    SplashView()
}

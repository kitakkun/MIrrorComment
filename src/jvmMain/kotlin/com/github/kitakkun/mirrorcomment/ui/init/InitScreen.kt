package com.github.kitakkun.mirrorcomment.ui.init

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.github.kitakkun.mirrorcomment.ui.commentviewer.CommentViewerScreen
import io.github.bonigarcia.wdm.WebDriverManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InitScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) {
            launch {
                WebDriverManager.chromedriver().setup()
                delay(800)
                navigator?.push(CommentViewerScreen())
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "アプリケーションの初期化中です．\nしばらくお待ちください．",
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(32.dp))
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "初回起動時は数分かかる場合があります．",
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

package com.github.kitakkun.mirrorcomment

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import com.github.kitakkun.mirrorcomment.di.mirrorCommentModule
import com.github.kitakkun.mirrorcomment.ui.commentviewer.CommentViewerScreen
import io.github.bonigarcia.wdm.WebDriverManager
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(mirrorCommentModule)
    }

    WebDriverManager.chromedriver().setup()

    Window(
        title = "MirrorComment",
        onCloseRequest = this::exitApplication,
        state = rememberWindowState(position = WindowPosition(Alignment.Center)),
    ) {
        MaterialTheme {
            Surface {
                Navigator(CommentViewerScreen())
            }
        }
    }
}

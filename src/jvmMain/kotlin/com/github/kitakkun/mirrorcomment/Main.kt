package com.github.kitakkun.mirrorcomment

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.github.kitakkun.mirrorcomment.di.mirrorCommentModule
import com.github.kitakkun.mirrorcomment.ui.commentviewer.CommentViewerPage
import com.github.kitakkun.mirrorcomment.ui.commentviewer.CommentViewerViewModel
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() = application {
    startKoin {
        modules(mirrorCommentModule)
    }

    Window(
        title = "MirrorComment",
        onCloseRequest = this::exitApplication,
        state = rememberWindowState(position = WindowPosition(Alignment.Center)),
    ) {
        MaterialTheme {
            Surface {
                val viewModel by remember { getKoin().inject<CommentViewerViewModel>() }
                CommentViewerPage(viewModel = viewModel)
            }
        }
    }
}

package com.github.kitakkun.mirrorcomment

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.kitakkun.mirrorcomment.di.mirrorCommentModule
import com.github.kitakkun.mirrorcomment.ui.commentviewer.CommentViewerPage
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(mirrorCommentModule)
    }

    Window(
        title = "MirrorComment",
        onCloseRequest = this::exitApplication,
    ) {
        MaterialTheme {
            Surface {
                CommentViewerPage()
            }
        }
    }
}

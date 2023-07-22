package com.github.kitakkun.mirrorcomment.ui.commentviewer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import org.koin.java.KoinJavaComponent

object CommentViewerScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel by remember { KoinJavaComponent.getKoin().inject<CommentViewerViewModel>() }
        CommentViewerPage(viewModel = viewModel)
    }
}

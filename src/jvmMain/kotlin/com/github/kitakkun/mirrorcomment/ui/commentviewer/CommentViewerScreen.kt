package com.github.kitakkun.mirrorcomment.ui.commentviewer

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class CommentViewerScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val viewModel = get<CommentViewerViewModel>()
        CommentViewerPage(viewModel = viewModel)
    }
}

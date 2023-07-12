package com.github.kitakkun.mirrorcomment.ui.commentviewer

import com.github.kitakkun.mirrorcomment.model.MirrativComment

data class CommentViewerState(
    val rawLiveUrl: String,
    val comments: List<MirrativComment> = emptyList(),
)

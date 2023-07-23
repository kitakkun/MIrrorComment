package com.github.kitakkun.mirrorcomment.ext

import androidx.compose.ui.graphics.Color

fun Color.toHexString(): String {
    return "#%02x%02x%02x".format(
        (this.red * 255).toInt(),
        (this.green * 255).toInt(),
        (this.blue * 255).toInt()
    )
}

fun String.toColor(): Color? {
    val longValue = when (this.length) {
        7 -> this.substring(1).toLong(16) or 0xFF000000 // for strings like #FFFFFF
        9 -> this.substring(1).toLong(16) // for strings like #FFFFFFFF
        else -> return null
    }
    return Color(longValue)
}

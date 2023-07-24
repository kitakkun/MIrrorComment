package com.github.kitakkun.mirrorcomment.preferences

import java.net.URL

fun String.isValidHttpOrHttpsUrl(): Boolean {
    return try {
        val url = URL(this)
        url.isValidHttpOrHttpsUrl()
    } catch (e: Exception) {
        false
    }
}

fun URL.isValidHttpOrHttpsUrl(): Boolean {
    return this.protocol == "http" || this.protocol == "https"
}

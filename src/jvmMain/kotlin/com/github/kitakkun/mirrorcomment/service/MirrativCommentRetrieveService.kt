package com.github.kitakkun.mirrorcomment.service

import com.github.kitakkun.mirrorcomment.coroutines.IOScope
import com.github.kitakkun.mirrorcomment.model.MirrativComment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver

class MirrativCommentRetrieveService(
    private val driver: ChromeDriver,
) : CoroutineScope by IOScope() {
    companion object {
        private const val BASE_URL = "https://www.mirrativ.com/live/"
        private const val RETRIEVE_INTERVAL_MILLIS = 1000L
    }

    private fun getLiveUrl(liveId: String) = "$BASE_URL$liveId?lang=ja"

    private val mutableNewCommentsFlow = MutableSharedFlow<List<MirrativComment>>()
    val newCommentsFlow = mutableNewCommentsFlow.asSharedFlow()

    private var loadedCommentSize = 0

    init {
        launch {
            while (true) {
                // ページ読み込み後新規で追加されたコメントを取得
                // dropはすでに取得済みのコメントを削除するため
                val newComments = driver
                    .findElements(By.cssSelector("._commentEnterActive_6t3be_16"))
                    .dropLast(loadedCommentSize) // すでに取得済みのコメントを除く
                    .reversed() // 古い→新しい順にする
                    .mapNotNull(MirrativComment::convert)
                mutableNewCommentsFlow.emit(newComments)
                loadedCommentSize += newComments.size
                delay(RETRIEVE_INTERVAL_MILLIS)
                if (isNeedToBeRefreshed()) {
                    driver.navigate().refresh()
                }
            }
        }
    }

    fun startCollecting(liveId: String) {
        driver.get(getLiveUrl(liveId))
    }

    fun stopCollecting() {
        if (driver.windowHandles.size > 1) {
            driver.close()
        }
    }

    private fun isNeedToBeRefreshed(): Boolean {
        return driver.findElements(By.cssSelector(".alertify"))?.isNotEmpty() ?: false
    }
}

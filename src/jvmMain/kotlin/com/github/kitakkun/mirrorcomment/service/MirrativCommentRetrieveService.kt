package com.github.kitakkun.mirrorcomment.service

import com.github.kitakkun.mirrorcomment.coroutines.IOScope
import com.github.kitakkun.mirrorcomment.model.MirrativComment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class MirrativCommentRetrieveService(
    private val driver: ChromeDriver,
    private val liveId: String,
) : CoroutineScope by IOScope() {
    companion object {
        private const val BASE_URL = "https://www.mirrativ.com/live/"
        private const val RETRIEVE_INTERVAL_MILLIS = 1000L
    }

    private val url: String get() = "$BASE_URL$liveId?lang=ja"

    private val mutableCommentFlow = MutableStateFlow<List<MirrativComment>>(emptyList())
    val commentFlow = mutableCommentFlow.asStateFlow()

    init {
        driver.get(url)
        WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("._comment_6t3be_3")))
        launch {
            while (true) {
                val comments = driver.findElements(By.cssSelector("._comment_6t3be_3"))
                    .mapNotNull { comment -> MirrativComment.convert(comment) }
                mutableCommentFlow.emit(comments)
                delay(RETRIEVE_INTERVAL_MILLIS)
                if (isNeedToBeRefreshed()) {
                    driver.navigate().refresh()
                    WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("._comment_6t3be_3")))
                }
            }
        }
    }

    fun dispose() {
        cancel()
        driver.quit()
    }

    private fun isNeedToBeRefreshed(): Boolean {
        return driver.findElements(By.cssSelector(".alertify")).isNotEmpty()
    }
}

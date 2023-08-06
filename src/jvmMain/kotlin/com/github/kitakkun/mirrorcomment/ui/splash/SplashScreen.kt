package com.github.kitakkun.mirrorcomment.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.github.kitakkun.mirrorcomment.ui.commentviewer.CommentViewerScreen
import io.github.bonigarcia.wdm.WebDriverManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : Screen {
    companion object {
        private const val MINIMUM_WAIT_TIME = 1500L
    }
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) {
            launch {
                val start = System.currentTimeMillis()
                WebDriverManager.chromedriver().setup()
                val finish = System.currentTimeMillis()
                val timeElapsed = finish - start
                if (timeElapsed < MINIMUM_WAIT_TIME) {
                    delay(MINIMUM_WAIT_TIME - timeElapsed)
                }
                navigator?.push(CommentViewerScreen())
            }
        }

        SplashView()
    }
}

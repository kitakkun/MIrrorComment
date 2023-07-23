package com.github.kitakkun.mirrorcomment.di

import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.AudioPlayer
import com.github.kitakkun.mirrorcomment.preferences.SettingsPropertiesRepository
import com.github.kitakkun.mirrorcomment.service.MirrativCommentRetrieveService
import com.github.kitakkun.mirrorcomment.ui.commentviewer.CommentViewerViewModel
import org.koin.dsl.module
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

val mirrorCommentModule = module {
    factory<ChromeDriver> {
        val options = ChromeOptions()
        options.addArguments("--headless")
        options.addArguments("--lang=ja-JP")
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver")
        ChromeDriver(options)
    }
    factory<MirrativCommentRetrieveService> { (liveId: String) ->
        MirrativCommentRetrieveService(get(), liveId)
    }
    factory<KtVoxApi> {(serverUrl: String) ->
        KtVoxApi.initialize(serverUrl)
    }
    single<SettingsPropertiesRepository> { SettingsPropertiesRepository() }
    single { AudioPlayer() }
    single { CommentViewerViewModel(get()) }
}

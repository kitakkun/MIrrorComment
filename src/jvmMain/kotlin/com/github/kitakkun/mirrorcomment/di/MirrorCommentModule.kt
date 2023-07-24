package com.github.kitakkun.mirrorcomment.di

import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.AudioPlayer
import com.github.kitakkun.mirrorcomment.preferences.SettingsRepository
import com.github.kitakkun.mirrorcomment.service.MirrativCommentRetrieveService
import com.github.kitakkun.mirrorcomment.ui.commentviewer.CommentViewerViewModel
import org.koin.dsl.module
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.util.*

val mirrorCommentModule = module {
    factory<ChromeDriver> {
        val options = ChromeOptions()
        options.addArguments("--headless")
        options.addArguments("--lang=ja-JP")
        ChromeDriver(options)
    }
    single<MirrativCommentRetrieveService> {
        MirrativCommentRetrieveService(get())
    }
    factory<Optional<KtVoxApi>> { (serverUrl: String) ->
        try {
            Optional.of(KtVoxApi.initialize(serverUrl))
        } catch (e: Throwable) {
            Optional.empty<KtVoxApi>()
        }
    }
    single<SettingsRepository> { SettingsRepository() }
    single { AudioPlayer() }
    single { CommentViewerViewModel(get()) }
}

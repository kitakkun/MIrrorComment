package com.github.kitakkun.mirrorcomment.di

import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.preferences.SettingsPropertiesRepository
import com.github.kitakkun.mirrorcomment.service.MirrativCommentRetrieveService
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
    single<KtVoxApi> {
        KtVoxApi.initialize("http://127.0.0.1:50021")
    }
    single<SettingsPropertiesRepository> { SettingsPropertiesRepository() }
}

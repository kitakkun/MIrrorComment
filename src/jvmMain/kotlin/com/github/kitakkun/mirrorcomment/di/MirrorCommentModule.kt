package com.github.kitakkun.mirrorcomment.di

import com.github.kitakkun.ktvox.api.KtVoxApi
import com.github.kitakkun.mirrorcomment.service.MirrativCommentRetrieveService
import org.koin.dsl.module
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

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
        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:50021")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(KtVoxApi::class.java)
    }
}

package com.pbj.sdk

import com.pbj.sdk.core.ApiEnvironment
import com.pbj.sdk.di.DataModule
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication

interface KoinTestContext {
    val apiKey: String
        get() = ""

    val environment: ApiEnvironment
        get() = ApiEnvironment.DEV

    val koinApp: KoinApplication
        get() = koinApplication {
            modules(DataModule.init(apiKey, environment))
        }
}
package com.pbj.sdk.di

import org.koin.core.Koin
import org.koin.core.component.KoinComponent

internal interface LiveKoinComponent : KoinComponent {

    override fun getKoin(): Koin {
        return LiveKoinContext.koinApp.koin
    }
}
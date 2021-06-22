package com.pbj.sdk.di

import com.pbj.sdk.core.SdkHolder
import org.koin.dsl.module

val trackingModule = module {
    single {
        SdkHolder.instance.tracker
    }
}
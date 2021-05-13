package com.pbj.sdk.di

import com.pbj.sdk.concreteImplementation.live.LiveApi
import com.pbj.sdk.concreteImplementation.live.LiveInteractorImpl
import com.pbj.sdk.concreteImplementation.live.LiveRepositoryImpl
import com.pbj.sdk.concreteImplementation.live.LiveWebSocketApi
import com.pbj.sdk.domain.live.LiveInteractor
import com.pbj.sdk.domain.live.LiveRepository
import com.pbj.sdk.live.LiveFeature
import com.pbj.sdk.live.LiveFeatureImpl
import org.koin.dsl.module

internal val liveModule = module {

    single { provideLiveRepository(get(), get()) }

    single { provideLiveInteractor(get()) }

    single { provideLiveFeature() }
}

internal fun provideLiveRepository(api: LiveApi, socketApi: LiveWebSocketApi): LiveRepository =
    LiveRepositoryImpl(api, socketApi)

internal fun provideLiveInteractor(liveRepository: LiveRepository): LiveInteractor =
    LiveInteractorImpl(liveRepository)

internal fun provideLiveFeature(): LiveFeature = LiveFeatureImpl()
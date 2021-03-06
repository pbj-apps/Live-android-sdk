package com.pbj.sdk.di

import com.pbj.sdk.concreteImplementation.vod.VodApi
import com.pbj.sdk.concreteImplementation.vod.VodInteractorImpl
import com.pbj.sdk.concreteImplementation.vod.VodRepositoryImpl
import com.pbj.sdk.domain.vod.VodInteractor
import com.pbj.sdk.domain.vod.VodRepository
import com.pbj.sdk.vod.VodFeature
import com.pbj.sdk.vod.VodFeatureImpl
import com.squareup.moshi.Moshi
import org.koin.dsl.module

internal val vodModule = module {

    single { provideVodRepository(get(), get()) }

    single { provideVodInteractor(get()) }

    single { provideVodFeature() }
}

internal fun provideVodRepository(api: VodApi, moshi: Moshi): VodRepository =
    VodRepositoryImpl(api, moshi)

internal fun provideVodInteractor(vodRepository: VodRepository): VodInteractor =
    VodInteractorImpl(vodRepository)

internal fun provideVodFeature(): VodFeature = VodFeatureImpl()


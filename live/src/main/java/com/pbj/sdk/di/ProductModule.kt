package com.pbj.sdk.di

import com.pbj.sdk.concreteImplementation.product.ProductApi
import com.pbj.sdk.concreteImplementation.product.ProductRepositoryImpl
import com.pbj.sdk.concreteImplementation.product.ProductWebSocketApi
import com.pbj.sdk.domain.product.ProductRepository
import com.pbj.sdk.product.ProductFeature
import com.pbj.sdk.product.ProductFeatureImpl
import com.squareup.moshi.Moshi
import org.koin.dsl.module

internal val productModule = module {

    single { provideProductRepository(get(), get(), get()) }

    single { provideProductFeature(get()) }
}

internal fun provideProductRepository(
    api: ProductApi,
    socketApi: ProductWebSocketApi,
    moshi: Moshi
): ProductRepository =
    ProductRepositoryImpl(api, socketApi, moshi)

internal fun provideProductFeature(repository: ProductRepository): ProductFeature =
    ProductFeatureImpl(repository)
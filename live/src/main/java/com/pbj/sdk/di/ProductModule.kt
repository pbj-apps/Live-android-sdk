package com.pbj.sdk.di

import com.pbj.sdk.concreteImplementation.product.ProductApi
import com.pbj.sdk.concreteImplementation.product.ProductRepositoryImpl
import com.pbj.sdk.concreteImplementation.product.ProductWebSocketApi
import com.pbj.sdk.domain.product.ProductRepository
import com.pbj.sdk.product.ProductFeature
import com.pbj.sdk.product.ProductFeatureImpl
import org.koin.dsl.module

internal val productModule = module {

    single { provideProductRepository(get(), get()) }

    single { provideProductFeature(get()) }
}

internal fun provideProductRepository(
    api: ProductApi,
    socketApi: ProductWebSocketApi
): ProductRepository =
    ProductRepositoryImpl(api, socketApi)

internal fun provideProductFeature(repository: ProductRepository): ProductFeature =
    ProductFeatureImpl(repository)
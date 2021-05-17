package com.pbj.sdk.product

import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.onErrorCallBack
import com.pbj.sdk.domain.onResult
import com.pbj.sdk.domain.product.ProductRepository
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.domain.product.model.ProductUpdate
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import timber.log.Timber

internal class ProductFeatureImpl(private val repository: ProductRepository): ProductFeature {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("$throwable")
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + errorHandler)

    override fun getProductsFor(
        episode: Episode,
        onError: onErrorCallBack?,
        onSuccess: ((List<Product>?) -> Unit)?
    ) {
        scope.launch {
            repository.fetchProductsFor(episode).onResult({
                onError?.invoke(it)
            }) {
                onSuccess?.invoke(it)
            }
        }
    }

    override fun getCurrentlyFeaturedProducts(
        episode: Episode,
        onError: onErrorCallBack?,
        onSuccess: ((List<Product>?) -> Unit)?
    ) {
        scope.launch {
            repository.fetchCurrentlyFeaturedProducts(episode).onResult({
                onError?.invoke(it)
            }) {
                onSuccess?.invoke(it)
            }
        }
    }

    override fun registerForProductHighlights(
        episode: Episode,
        onResult: ((ProductUpdate) -> Unit)?
    ) {
        scope.launch {
            repository.observeProductHighlights(episode)
                .filterNotNull()
                .collect {
                    onResult?.invoke(it)
                }
        }
    }

    override fun unRegisterProductHighlights(episode: Episode) {
        scope.launch {
            repository.unRegisterProductHighlights(episode)
        }
    }
}
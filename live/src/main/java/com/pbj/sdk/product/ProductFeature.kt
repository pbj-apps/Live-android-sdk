package com.pbj.sdk.product

import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.onErrorCallBack
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.domain.product.model.ProductUpdate

interface ProductFeature {

    fun getProductsFor(
        episode: Episode,
        onError: onErrorCallBack? = null,
        onSuccess: ((List<Product>?) -> Unit)? = null
    )

    fun getCurrentlyFeaturedProducts(
        episode: Episode,
        onError: onErrorCallBack? = null,
        onSuccess: ((List<Product>?) -> Unit)? = null
    )

    fun registerForProductHighlights(
        episode: Episode,
        onResult: ((ProductUpdate) -> Unit)? = null
    )

    fun unRegisterProductHighlights(episode: Episode)
}
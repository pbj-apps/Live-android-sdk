package com.pbj.sdk.product

import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.onErrorCallBack
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.domain.product.model.ProductUpdate
import com.pbj.sdk.domain.vod.model.VodVideo

interface ProductFeature {

    fun getProductsFor(
        episode: Episode,
        onError: onErrorCallBack? = null,
        onSuccess: ((List<Product>?) -> Unit)? = null
    )

    fun getProductsFor(
        video: VodVideo,
        onError: onErrorCallBack? = null,
        onSuccess: ((List<Product>?) -> Unit)? = null
    )

    fun getHighlightedProducts(
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
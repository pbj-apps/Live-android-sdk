package com.pbj.sdk.domain.product

import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.domain.product.model.ProductUpdate
import com.pbj.sdk.domain.vod.model.VodVideo
import com.pbj.sdk.videoPlayer.VideoViewModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun fetchProductsFor(episode: Episode): Result<List<Product>>
    suspend fun fetchProductsFor(video: VodVideo): Result<List<Product>>
    suspend fun fetchHighlightedProducts(episode: Episode): Result<List<Product>>
    suspend fun observeProductHighlights(episode: Episode): Flow<ProductUpdate>
    suspend fun unRegisterProductHighlights(episode: Episode)
}
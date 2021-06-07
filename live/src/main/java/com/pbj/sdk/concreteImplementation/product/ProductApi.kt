package com.pbj.sdk.concreteImplementation.product

import com.pbj.sdk.concreteImplementation.product.model.JsonProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ProductApi {

    @GET("shopping/episodes/featured-products")
    suspend fun fetchProductsForEpisode(@Query("episode") episodeId: String): Response<JsonProductResponse>

    @GET("shopping/videos/featured-products")
    suspend fun fetchProductsForVideo(@Query("video") episodeId: String): Response<JsonProductResponse>

    @GET("shopping/episodes/featured-products/highlighted")
    suspend fun fetchHighlightedProducts(@Query("episode") episodeId: String): Response<JsonProductResponse>
}
package com.pbj.sdk.concreteImplementation.product

import com.pbj.sdk.concreteImplementation.product.model.JsonProduct
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ProductApi {

    @GET("/integrations/shopify/episodes/featured-products")
    suspend fun fetchProducts(@Query("episode") episodeId: String): Response<List<JsonProduct>>

    @GET("/integrations/shopify/episodes/featured-products/highlighted")
    suspend fun fetchCurrentlyFeaturedProducts(@Query("episode") episodeId: String): Response<List<JsonProduct>>
}
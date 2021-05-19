package com.pbj.sdk.concreteImplementation.product.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonProductResponse(
    val count: Int,
    val results: List<JsonProductItem>?
)

@JsonClass(generateAdapter = true)
internal data class JsonProductItem(
    val id: String,
    val product: JsonProduct?
)

@JsonClass(generateAdapter = true)
internal data class JsonProduct(
    val id: String,
    val title: String?,
    val price: String?,
    val description: String?,
    val store_url: String?,
    val image: JsonProductImage?
)

@JsonClass(generateAdapter = true)
internal data class JsonProductImage(
    val src: String
)
package com.pbj.sdk.concreteImplementation.product.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonProductResult(
    val id: String,
    val product: JsonProduct
)

@JsonClass(generateAdapter = true)
internal data class JsonProduct(
    val id: String,
    val title: String,
    val price: String,
    val detail: String,
    val store_url: String,
    val image: JsonProductImage
)

@JsonClass(generateAdapter = true)
internal data class JsonProductImage(
    val src: String
)
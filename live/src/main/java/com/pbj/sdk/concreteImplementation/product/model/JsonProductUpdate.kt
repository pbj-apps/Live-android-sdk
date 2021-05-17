package com.pbj.sdk.concreteImplementation.product.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonProductUpdate(
    val productList: List<JsonProduct>
)
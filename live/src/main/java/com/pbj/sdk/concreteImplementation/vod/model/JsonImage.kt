package com.pbj.sdk.concreteImplementation.vod.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonImage(
    val full_size: String,
    val medium: String,
    val small: String
)
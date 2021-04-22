package com.pbj.sdk.concreteImplementation.vod.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonProfileImage(
    val id: String,
    val image: JsonImage?,
    val image_poi: String?
)
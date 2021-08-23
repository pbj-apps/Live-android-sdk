package com.pbj.sdk.concreteImplementation.vod.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonVodItemResponse(
    val next: String? = null,
    val results: List<JsonVodWrapper>? = null
)
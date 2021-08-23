package com.pbj.sdk.domain.vod.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VodItemResponse(
    val next: String? = null,
    val results: List<VodItem>
)


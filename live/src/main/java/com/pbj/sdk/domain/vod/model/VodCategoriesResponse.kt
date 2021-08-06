package com.pbj.sdk.domain.vod.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VodCategoriesResponse(
    val next: String? = null,
    val results: List<VodCategory>
)


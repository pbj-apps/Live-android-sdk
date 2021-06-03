package com.pbj.sdk.domain.vod.model

data class VodCategory(
        val id: String,
        val title: String,
        val items: List<VodItem>,
        val featuredItems: List<VodItem>
)

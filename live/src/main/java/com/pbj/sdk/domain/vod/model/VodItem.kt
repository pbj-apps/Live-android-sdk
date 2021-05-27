package com.pbj.sdk.domain.vod.model

import java.net.URL

interface VodItem {
    val id: String
    val title: String
    val description: String?
    val type: VodItemType?
    val thumbnailUrl: String?
}

enum class VodItemType {
    Playlist,
    Video
}
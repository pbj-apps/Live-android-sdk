package com.pbj.sdk.domain.vod.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface VodItem : Parcelable {
    val id: String
    val title: String
    val description: String?
    val type: VodItemType?
    val thumbnailUrl: String?
}

interface VodSearchItem : Parcelable {
    val id: String
    val title: String
    val description: String?
    val type: VodItemType?
    val thumbnailUrl: String?
}

@Parcelize
enum class VodItemType : Parcelable {
    Playlist,
    Video,
    Category
}
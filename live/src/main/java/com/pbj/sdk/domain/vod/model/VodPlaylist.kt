package com.pbj.sdk.domain.vod.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.net.URL

@Parcelize
data class VodPlaylist(
        override val id: String,
        override val title: String,
        override val description: String?,
        override val type: VodItemType? = VodItemType.Playlist,
        override val thumbnailUrl: String?,
        val videoList: List<VodVideo>,
        val videoCount: Int,
        val isFeatured: Boolean = false
) : Parcelable, VodItem
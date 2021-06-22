package com.pbj.sdk.domain.vod.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.net.URL

@Parcelize
data class VodVideo(
    override val id: String,
    override val title: String,
    override val description: String?,
    override val thumbnailUrl: String?,
    val largeImageUrl: String?,
    val videoURL: String?,
    val duration: Int?,
    override val type: VodItemType? = VodItemType.Video,
    val instructorList: List<Instructor> = listOf(),
    val playlists: List<VodPlaylist>? = null,
    val categories: List<VodCategory>? = null,
    val isFeatured: Boolean = false
) : Parcelable, VodItem
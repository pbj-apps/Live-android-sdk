package com.pbj.sdk.domain.vod.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.net.URL

@Parcelize
data class VodVideo(
    override val id: String,
    override val title: String,
    override val description: String?,
    override val thumbnailUrl: URL?,
    val largeImageUrl: URL?,
    val videoURL: URL?,
    val duration: Int?,
    override val type: VodItemType? = VodItemType.Video,
    val instructorList: List<Instructor> = listOf(),
    val isFeatured: Boolean = false
) : Parcelable, VodItem
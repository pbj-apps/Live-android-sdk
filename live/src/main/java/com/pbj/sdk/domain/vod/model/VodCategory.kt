package com.pbj.sdk.domain.vod.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VodCategory(
    override val id: String,
    override val title: String,
    override val description: String?,
    val items: List<VodItem>,
    val featuredItems: List<VodItem>,
    override val type: VodItemType? = VodItemType.Category,
    override val thumbnailUrl: String? = null
) : VodItem, Parcelable

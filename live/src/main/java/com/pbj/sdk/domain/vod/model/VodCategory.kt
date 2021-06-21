package com.pbj.sdk.domain.vod.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VodCategory(
    val id: String,
    val title: String,
    val items: List<VodItem>,
    val featuredItems: List<VodItem>
): Parcelable

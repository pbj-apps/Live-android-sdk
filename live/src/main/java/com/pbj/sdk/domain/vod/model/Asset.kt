package com.pbj.sdk.domain.vod.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asset(
    val id: String,
    val assetType: String?,
    val assetUrl: String?,
    val image: Image?,
    val imagePoi: String?,
    val status: String?
) : Parcelable
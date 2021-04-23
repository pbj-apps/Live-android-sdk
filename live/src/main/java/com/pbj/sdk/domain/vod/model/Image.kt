package com.pbj.sdk.domain.vod.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val fullSize: String,
    val medium: String,
    val small: String
) : Parcelable
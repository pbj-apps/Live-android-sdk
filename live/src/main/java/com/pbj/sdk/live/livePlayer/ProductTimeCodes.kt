package com.pbj.sdk.live.livePlayer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductTimeCodes(
    val productId: String,
    val startTime: Long,
    val endTime: Long
) : Parcelable

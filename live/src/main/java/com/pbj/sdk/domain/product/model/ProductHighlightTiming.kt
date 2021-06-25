package com.pbj.sdk.domain.product.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class ProductHighlightTiming(
    val startTime: LocalTime?,
    val endTime: LocalTime?
): Parcelable
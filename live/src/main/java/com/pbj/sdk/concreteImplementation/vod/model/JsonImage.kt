package com.pbj.sdk.concreteImplementation.vod.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
internal data class JsonImage(
    val full_size: String,
    val medium: String,
    val small: String
) : Parcelable
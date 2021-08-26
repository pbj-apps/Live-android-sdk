package com.pbj.sdk.concreteImplementation.vod.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
internal data class JsonPreviewAsset(
    val id: String,
    val asset_type: String,
    val asset_url: String,
    val image: JsonImage,
    val image_poi: String,
    val status: String
) : Parcelable
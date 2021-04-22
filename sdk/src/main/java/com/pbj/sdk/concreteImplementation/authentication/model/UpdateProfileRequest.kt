package com.pbj.sdk.concreteImplementation.authentication.model

import com.squareup.moshi.JsonClass
import okhttp3.MultipartBody

@JsonClass(generateAdapter = true)
internal data class UpdateProfileRequest(
    val first_name: String,
    val last_name: String
)

@JsonClass(generateAdapter = true)
internal data class UpdateProfileImageRequest(
    val image: MultipartBody.Part
)
package com.pbj.sdk.concreteImplementation.authentication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ChangePasswordRequest(
    val current_password: String,
    val new_password: String
)
package com.pbj.sdk.concreteImplementation.authentication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonLoginRequest(
    val email: String,
    val password: String
)
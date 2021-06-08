package com.pbj.sdk.concreteImplementation.authentication.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonLoginResponse(

    val id: String,

    @Json(name = "auth_token") val authToken: String,

    val email: String,

    @Json(name = "first_name") val firstName: String,

    @Json(name = "last_name") val lastName: String
)
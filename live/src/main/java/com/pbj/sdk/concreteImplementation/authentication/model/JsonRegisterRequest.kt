package com.pbj.sdk.concreteImplementation.authentication.model

import com.pbj.sdk.domain.authentication.model.RegisterRequest
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonRegisterRequest(
    val first_name: String,
    val last_name: String,
    val username: String,
    val email: String,
    val password: String
)

internal val RegisterRequest.asJson: JsonRegisterRequest
    get() = JsonRegisterRequest(
        firstName,
        lastName,
        username,
        email,
        password
    )
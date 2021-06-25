package com.pbj.sdk.domain.authentication.model

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String
)
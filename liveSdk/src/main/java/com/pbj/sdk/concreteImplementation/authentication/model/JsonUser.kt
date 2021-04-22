package com.pbj.sdk.concreteImplementation.authentication.model

import com.pbj.sdk.concreteImplementation.vod.model.JsonProfileImage
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonUser(
    val id: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val username: String,
    val profile_image: JsonProfileImage?,
    val is_verified: Boolean = false,
    val is_staff: Boolean = false,
    val auth_token: String?,
    val is_survey_attempted: Boolean = false
)
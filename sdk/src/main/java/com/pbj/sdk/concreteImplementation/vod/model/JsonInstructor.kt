package com.pbj.sdk.concreteImplementation.vod.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonInstructor(
    val id: String,
    val dob: String? = null,
    val email: String? = null,
    val first_name: String? = null,
    val is_content_programmer: Boolean = false,
    val is_instructor: Boolean = false,
    val is_staff: Boolean = false,
    val is_survey_attempted: Boolean = false,
    val is_verified: Boolean = false,
    val last_name: String? = null,
    val profile_image: JsonProfileImage? = null,
    val subscription: String? = null,
    val username: String? = null
)
package com.pbj.sdk.concreteImplementation.live.model

import com.pbj.sdk.concreteImplementation.vod.model.JsonProfileImage
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
internal data class JsonStreamer(
    val id: String,
    val dob: LocalDate?,
    val email: String?,
    val first_name: String?,
    val is_content_programmer: Boolean?,
    val is_instructor: Boolean?,
    val is_staff: Boolean?,
    val is_survey_attempted: Boolean?,
    val is_verified: Boolean?,
    val last_name: String?,
    val profile_image: JsonProfileImage?,
    val subscription: String?,
    val username: String?
)
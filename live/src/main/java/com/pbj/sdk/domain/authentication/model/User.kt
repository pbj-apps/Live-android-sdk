package com.pbj.sdk.domain.authentication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
     val id: String,
     val firstname: String,
     val lastname: String,
     val email: String,
     val username: String,
     val hasAnsweredSurvey: Boolean,
     val avatarUrl: String? = null,
     val authToken: String? = null
)

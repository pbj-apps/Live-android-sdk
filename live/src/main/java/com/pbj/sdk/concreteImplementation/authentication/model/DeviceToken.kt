package com.pbj.sdk.concreteImplementation.authentication.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceToken(val token: String)
package com.pbj.sdk.concreteImplementation.organization.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class FeatureFlags(
    val is_chat_enabled: Boolean = false
)
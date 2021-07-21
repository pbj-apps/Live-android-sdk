package com.pbj.sdk.domain.organization.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrganizationConfig(
    val isChatEnabled: Boolean
)
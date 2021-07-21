package com.pbj.sdk.concreteImplementation.organization.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonOrganizationConfig(
    val feature_flags: FeatureFlags?
)
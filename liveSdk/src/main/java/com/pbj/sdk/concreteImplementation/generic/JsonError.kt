package com.pbj.sdk.concreteImplementation.generic

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonGenericError(
    val errors: List<JsonError> = listOf()
)

@JsonClass(generateAdapter = true)
internal data class JsonError(
    val field: String?,
    val message: String?
)
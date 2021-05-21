package com.pbj.sdk.concreteImplementation.live.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonWebSocketRequest(val command: String)

@JsonClass(generateAdapter = true)
internal data class JsonWebSocketProductRequest(
    val command: String,
    val episode_id: String
    )
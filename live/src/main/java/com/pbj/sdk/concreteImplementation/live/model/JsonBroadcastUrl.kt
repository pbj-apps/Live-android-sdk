package com.pbj.sdk.concreteImplementation.live.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonBroadcastUrl(
    val broadcast_url: String? = null,
    val elapsed_time: String? = null
)
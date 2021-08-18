package com.pbj.sdk.concreteImplementation.live.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonBroadcastUrl(
    val stream_type: String? = null,
    val broadcast_url: String? = null,
    val elapsed_time: String? = null
)
package com.pbj.sdk.concreteImplementation.live.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonEpisodeStatusUpdate(
    val command: String,
    val episode: JsonEpisodeUpdate,
    val extra: JsonExtra,
    val stream_status: String
)
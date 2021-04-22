package com.pbj.sdk.concreteImplementation.live.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonEpisode(
    val id: String,
    val description: String?,
    val chat_mode: String?,
    val duration: Int?,
    val starting_at: String?,
    val ends_at: String?,
    val show: JsonShow?,
    val status: String?,
    val stream_status: String?,
    val streamer: JsonStreamer?,
    val title: String?
)
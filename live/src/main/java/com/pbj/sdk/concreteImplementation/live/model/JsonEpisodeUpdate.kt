package com.pbj.sdk.concreteImplementation.live.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonEpisodeUpdate(
    val id: String,
    val description: String,
    val show_id: String,
    val status: String,
    val title: String,
    val waiting_room_description: String
)
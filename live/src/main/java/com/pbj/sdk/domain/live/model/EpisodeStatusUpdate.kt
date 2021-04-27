package com.pbj.sdk.domain.live.model

data class EpisodeStatusUpdate(
    val id: String,
    val showId: String,
    val waitingRoomDescription: String,
    val status: EpisodeStatus,
)
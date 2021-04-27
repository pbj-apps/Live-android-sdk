package com.pbj.sdk.domain.live.model

data class EpisodeResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Episode>
)
package com.pbj.sdk.domain.live.model

data class BroadcastUrl(
    val streamType: StreamType,
    val broadcastUrl: String? = null,
    val elapsedTime: Long? = null
)

enum class StreamType {
    Video,
    Live
}
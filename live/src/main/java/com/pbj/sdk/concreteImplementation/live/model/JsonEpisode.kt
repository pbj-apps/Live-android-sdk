package com.pbj.sdk.concreteImplementation.live.model

import com.pbj.sdk.concreteImplementation.vod.model.JsonPreviewAsset
import com.pbj.sdk.concreteImplementation.vod.model.JsonVodVideo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonEpisode(
    val id: String,
    val title: String?,
    val description: String?,
    val chat_mode: String?,
    val duration: Int?,
    val preview_asset: JsonPreviewAsset?,
    val starting_at: String?,
    val ends_at: String?,
    val show: JsonShow?,
    val status: String?,
    val stream_status: String?,
    val streamer: JsonStreamer?,
    val pre_recorded_video: JsonVodVideo?,
    val is_push_notification_enabled: Boolean = false
)
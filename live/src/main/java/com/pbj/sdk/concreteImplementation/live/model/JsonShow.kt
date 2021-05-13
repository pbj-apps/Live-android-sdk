package com.pbj.sdk.concreteImplementation.live.model

import com.pbj.sdk.concreteImplementation.vod.model.JsonAsset
import com.pbj.sdk.concreteImplementation.vod.model.JsonInstructor
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonShow(
    val id: String,
    val title: String?,
    val description: String?,
    val waiting_room_description: String?,
    val duration: Int?,
    val start_date: String?,
    val end_date: String?,
    val instructors: List<JsonInstructor>?,
    val preview_asset: JsonAsset?,
    val preview_image_url: String?,
    val preview_url_type: String?,
    val preview_video_url: String?,
    val rrule_schedule: String?
)
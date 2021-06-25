package com.pbj.sdk.concreteImplementation.vod.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonVodVideo(
    override val id: String,
    override val title: String,
    override val description: String,
    val asset: JsonAsset,
    val categories: List<JsonVodCategory>?,
    val playlists: List<JsonVodPlaylist>?,
    val duration: Int?,
    val preview_asset: JsonPreviewAsset? = null,
    val status: String,
    val instructors: List<JsonInstructor>? = listOf(),
    override val asset_type: String = "video"
): JsonVodItem
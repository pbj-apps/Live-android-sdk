package com.pbj.sdk.concreteImplementation.vod.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonVodPlaylist(
    override val id: String,
    override val title: String,
    override val description: String?,
    val video_count: Int = 0,
    val videos: List<JsonVodVideo>? = null,
    val preview_asset: JsonPreviewAsset? = null,
    override val asset_type: String? = null,
    val is_featured: Boolean = false
): JsonVodItem
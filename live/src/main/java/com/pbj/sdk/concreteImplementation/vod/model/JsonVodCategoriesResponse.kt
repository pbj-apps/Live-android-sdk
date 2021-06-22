package com.pbj.sdk.concreteImplementation.vod.model

import com.pbj.sdk.domain.vod.model.VodCategory
import com.pbj.sdk.domain.vod.model.VodItem
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonVodCategoriesPage(
    val results: List<JsonVodCategory>
)

@JsonClass(generateAdapter = true)
internal data class JsonVodCategory(
    val id: String,
    val title: String,
    val description: String,
    val items: List<JsonVodVideoOrPlaylist>? = null,
    val featured_items: List<JsonVodVideoOrPlaylist>? = null
)

@JsonClass(generateAdapter = true)
internal data class JsonVodVideoOrPlaylist(
    val item_type: String,
    val item: JsonVodVideoOrPlaylistItem
)

@JsonClass(generateAdapter = true)
internal data class JsonVodVideoOrPlaylistItem(
    val id: String,
    val title: String,
    val description: String,
    val preview_asset: JsonPreviewAsset?,
    val asset: JsonAsset?,
    val video_count: Int?,
    val duration: Int?
)

internal val JsonVodCategory.asModel: VodCategory
    get() = VodCategory(
        id = id,
        title = title,
        items = items?.mapNotNull { it.asModel } ?: listOf(),
        featuredItems = featured_items?.mapNotNull { it.asModel } ?: listOf()
    )


internal val JsonVodVideoOrPlaylist.asModel: VodItem?
    get() = when (item_type) {
        "playlist" -> {
            VodPlaylist(
                id = item.id,
                title = item.title,
                description = item.description,
                thumbnailUrl = item.preview_asset?.image?.small,
                videoList = listOf(),
                videoCount = item.video_count ?: 0
            )
        }
        "video" -> {
            VodVideo(
                id = item.id,
                title = item.title,
                description = item.description,
                thumbnailUrl = item.preview_asset?.image?.small,
                largeImageUrl = item.preview_asset?.image?.small,
                videoURL = item.asset?.asset_url,
                duration = item.duration ?: 0,
                instructorList = listOf()
            )
        }
        else -> null
    }



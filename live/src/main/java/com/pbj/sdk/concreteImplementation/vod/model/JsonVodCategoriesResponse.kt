package com.pbj.sdk.concreteImplementation.vod.model

import com.pbj.sdk.domain.vod.model.*
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonVodCategoriesPage(
    val next: String? = null,
    val results: List<JsonVodCategory>? = null
)

@JsonClass(generateAdapter = true)
internal data class JsonVodCategory(
    override val id: String,
    override val title: String,
    override val description: String,
    val items: List<JsonVodWrapper>? = null,
    val featured_items: List<JsonVodWrapper>? = null,
    override val asset_type: String?,
    override val item_type: String?
) : JsonVodItem

@JsonClass(generateAdapter = true)
internal data class JsonVodWrapper(
    val item_type: String,
    val item: JsonVodItemImpl
)

@JsonClass(generateAdapter = true)
internal data class JsonVodItemImpl(
    val id: String,
    val title: String,
    val description: String,
    val items: List<JsonVodWrapper>? = null,
    val featured_items: List<JsonVodWrapper>? = null,
    val asset_type: String?,
    val item_type: String?,
    val video_count: Int = 0,
    val videos: List<JsonVodVideo>? = null,
    val is_featured: Boolean = false,
    val asset: JsonAsset,
    val categories: List<JsonVodCategory>?,
    val playlists: List<JsonVodPlaylist>?,
    val duration: Int?,
    val preview_asset: JsonPreviewAsset? = null,
    val status: String,
    val instructors: List<JsonInstructor>? = listOf()
)

internal val JsonVodCategoriesPage.asModel: VodCategoriesResponse
    get() = VodCategoriesResponse(
        next = next,
        results = results?.map { it.asModel } ?: listOf()
    )

internal val JsonVodCategory.asModel: VodCategory
    get() = VodCategory(
        id = id,
        title = title,
        items = items?.mapNotNull { it.asModel } ?: listOf(),
        featuredItems = featured_items?.mapNotNull { it.asModel } ?: listOf(),
        description = description,
        type = VodItemType.Category,
        thumbnailUrl = null
    )


internal val JsonVodWrapper.asModel: VodItem?
    get() = when (item_type) {
        "playlist" -> {
            VodPlaylist(
                id = item.id,
                title = item.title,
                description = item.description,
                type = VodItemType.Playlist,
                thumbnailUrl = item.preview_asset?.image?.small,
                videoList = item.videos?.map { it.asModel } ?: listOf(),
                videoCount = item.video_count,
                isFeatured = item.is_featured
            )
        }
        "video" -> {
            VodVideo(
                id = item.id,
                title = item.title,
                description = item.description,
                thumbnailUrl = item.preview_asset?.image?.small,
                largeImageUrl = item.preview_asset?.image?.small,
                videoURL = item.asset.asset_url,
                duration = item.duration ?: 0,
                instructorList = listOf(),
                type = VodItemType.Video,
                playlists = item.playlists?.map { it.asModel } ?: listOf(),
                categories = item.categories?.map { it.asModel } ?: listOf(),
                isFeatured = item.is_featured
            )
        }
        "category" -> {
            VodCategory(
                id = item.id,
                title = item.title,
                description = item.description,
                items = item.items?.mapNotNull { it.asModel } ?: listOf(),
                featuredItems = item.featured_items?.mapNotNull { it.asModel } ?: listOf(),
                type = VodItemType.Category,
                thumbnailUrl = null
            )
        }
        else -> null
    }



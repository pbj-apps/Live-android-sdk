package com.pbj.sdk.concreteImplementation.vod.model

import com.pbj.sdk.domain.vod.model.*

internal val JsonVodPlaylist.asModel: VodPlaylist
    get() = VodPlaylist(
        id,
        title,
        description,
        vodItemType,
        preview_asset?.image?.medium,
        videos?.asVodVideoList ?: listOf(),
        video_count
    )

internal val List<JsonVodVideo>.asVodVideoList
    get() = map { it.asModel }

internal val JsonVodVideo.asModel: VodVideo
    get() = VodVideo(
        id = id,
        title = title,
        description = description,
        thumbnailUrl = preview_asset?.image?.medium,
        largeImageUrl = preview_asset?.image?.full_size,
        videoURL = asset?.asset_url,
        duration = duration,
        type = vodItemType,
        instructorList = instructors?.asInstructorList ?: listOf(),
        playlists = playlists?.map { it.asModel },
        categories = categories?.map { it.asModel }
    )

internal val JsonVodItemResponse.asModel: VodItemResponse
    get() = VodItemResponse(next, results?.mapNotNull { it.asModel } ?: listOf())

internal val JsonVodItemImpl.asModel: VodItem?
    get() = when (item_type) {
        "playlist" -> {
            VodPlaylist(
                id = id,
                title = title,
                description = description,
                type = VodItemType.Playlist,
                thumbnailUrl = preview_asset?.image?.small,
                videoList = videos?.map { it.asModel } ?: listOf(),
                videoCount = video_count,
                isFeatured = is_featured
            )
        }
        "video" -> {
            VodVideo(
                id = id,
                title = title,
                description = description,
                thumbnailUrl = preview_asset?.image?.small,
                largeImageUrl = preview_asset?.image?.small,
                videoURL = asset?.asset_url,
                duration = duration ?: 0,
                instructorList = listOf(),
                type = VodItemType.Video,
                playlists = playlists?.map { it.asModel } ?: listOf(),
                categories = categories?.map { it.asModel } ?: listOf(),
                isFeatured = is_featured
            )
        }
        "category" -> {
            VodCategory(
                id = id,
                title = title,
                description = description,
                items = items?.mapNotNull { it.asModel } ?: listOf(),
                featuredItems = featured_items?.mapNotNull { it.asModel } ?: listOf(),
                type = VodItemType.Category,
                thumbnailUrl = null
            )
        }
        else -> null
    }

internal val JsonVodItem.vodItemType: VodItemType?
    get() = when (asset_type) {
        "video" -> VodItemType.Video
        "playlist" -> VodItemType.Playlist
        else -> null
    }

internal val List<JsonInstructor>.asInstructorList: List<Instructor>
    get() = map { it.asModel }

internal val JsonInstructor.asModel: Instructor
    get() = Instructor(
        id,
        first_name,
        last_name,
        username,
        email,
        is_content_programmer,
        is_instructor,
        is_staff,
        is_survey_attempted,
        is_verified,
        profile_image?.asModel
    )

internal val JsonProfileImage.asModel: ProfileImage
    get() = ProfileImage(id, image?.full_size, image?.medium, image?.small)

internal val JsonAsset.asModel: Asset
    get() = Asset(id, asset_type, asset_url, image?.asModel, image_poi, status)

internal val JsonImage.asModel: Image
    get() = Image(full_size, medium, small)
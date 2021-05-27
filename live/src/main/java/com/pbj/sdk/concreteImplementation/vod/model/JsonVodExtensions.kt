package com.pbj.sdk.concreteImplementation.vod.model

import com.pbj.sdk.domain.vod.model.*
import java.net.URL

internal val JsonVodPlaylist.asModel: VodPlaylist
    get() = VodPlaylist(
        id,
        title,
        description,
        vodItemType,
        preview_asset.image.medium,
        videos.asVodVideoList,
        video_count
    )

internal val List<JsonVodVideo>.asVodVideoList
    get() = map { it.asModel }

internal val JsonVodVideo.asModel: VodVideo
    get() = VodVideo(
        id = id,
        title = title,
        description = description,
        thumbnailUrl = preview_asset.image.medium,
        largeImageUrl = preview_asset.image.full_size,
        videoURL = asset.asset_url,
        duration = duration,
        type = vodItemType,
        instructorList = instructors?.asInstructorList ?: listOf()
    )

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
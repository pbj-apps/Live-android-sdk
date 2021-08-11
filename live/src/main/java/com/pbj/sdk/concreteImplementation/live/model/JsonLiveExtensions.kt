package com.pbj.sdk.concreteImplementation.live.model

import com.pbj.sdk.concreteImplementation.vod.model.asInstructorList
import com.pbj.sdk.concreteImplementation.vod.model.asModel
import com.pbj.sdk.domain.live.model.*
import com.pbj.sdk.utils.DateUtils
import com.pbj.sdk.utils.asMilliSeconds

internal val JsonEpisodeResponse.asModel: EpisodeResponse
    get() = EpisodeResponse(
        count,
        next,
        previous,
        results.asEpisodeList
    )

internal val JsonEpisode.asModel: Episode
    get() {
        val chatMode: ChatMode = when (chat_mode) {
            "enabled" -> ChatMode.ENABLED
            "disabled" -> ChatMode.DISABLED
            "qna" -> ChatMode.QNA
            else -> ChatMode.DISABLED
        }

        return Episode(
            chatMode = chatMode,
            id = id,
            title = title,
            description = description,
            duration = duration,
            image = preview_asset.image.asModel,
            startDate = DateUtils.getDateTime(starting_at),
            endDate = DateUtils.getDateTime(ends_at),
            show = show?.asModel,
            status = getLiveStatus(status),
            streamer = streamer?.asModel,
            video = pre_recorded_video?.asModel,
            hasReminder = is_push_notification_enabled
        )
    }

internal val JsonShow.asModel: Show
    get() = Show(
        id,
        title,
        description,
        waiting_room_description,
        duration,
        start_date,
        end_date,
        instructors?.asInstructorList,
        preview_asset?.asModel,
        preview_image_url,
        preview_url_type,
        preview_video_url,
        rrule_schedule
    )

private val JsonStreamer.asModel: Streamer
    get() = Streamer(
        id,
        dob,
        email,
        first_name,
        last_name,
        username,
        is_content_programmer,
        is_instructor,
        is_staff,
        is_survey_attempted,
        is_verified,
        profile_image?.asModel
    )

private val List<JsonEpisode>.asEpisodeList: List<Episode>
    get() = map { it.asModel }

internal val JsonEpisodeStatusUpdate.asModel: EpisodeStatusUpdate
    get() = with(episode) {
        EpisodeStatusUpdate(
            id = id,
            waitingRoomDescription = waiting_room_description,
            showId = show_id,
            status = getLiveStatus(status)
        )
    }

internal val JsonBroadcastUrl.asModel: BroadcastUrl
    get() {
        val time = DateUtils.getLocalTime(elapsed_time).asMilliSeconds
       return BroadcastUrl(broadcast_url, time)
    }

private fun getLiveStatus(status: String?): EpisodeStatus = when (status) {
    "waiting_room" -> EpisodeStatus.WAITING_ROOM
    "broadcasting" -> EpisodeStatus.BROADCASTING
    "finished" -> EpisodeStatus.FINISHED
    else -> EpisodeStatus.IDLE
}
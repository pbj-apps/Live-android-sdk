package com.pbj.sdk.domain.live

import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.EpisodeResponse
import com.pbj.sdk.domain.live.model.EpisodeStatusUpdate
import com.pbj.sdk.domain.live.model.Show
import com.pbj.sdk.domain.onErrorCallBack

internal interface LiveInteractor {

    val liveRepository: LiveRepository

    fun getLiveStreams(
        onError: onErrorCallBack? = null,
        onSuccess: ((EpisodeResponse?) -> Unit)? = null
    )

    fun getLiveStreamsSchedule(
        date: String,
        onError: onErrorCallBack? = null,
        onSuccess: ((EpisodeResponse?) -> Unit)? = null
    )

    fun getLiveStreamsSchedule(
        daysAhead: Int = 7,
        onError: onErrorCallBack? = null,
        onSuccess: ((EpisodeResponse?) -> Unit)? = null
    )

    fun getCurrentEpisode(
        onError: onErrorCallBack? = null,
        onSuccess: ((Episode?) -> Unit)? = null
    )

    fun getCurrentEpisode(
        showId: String,
        onError: onErrorCallBack? = null,
        onSuccess: ((Episode?) -> Unit)? = null
    )

    fun getShowPublic(
        showId: String,
        onError: onErrorCallBack? = null,
        onSuccess: ((Show?) -> Unit)? = null
    )

    fun registerForRealTimeLiveStreamUpdates(
        onResult: ((EpisodeStatusUpdate) -> Unit)? = null
    )

    fun leaveRealTimeLiveStreamUpdates()

    fun getBroadcastUrl(
        episode: Episode,
        onError: onErrorCallBack? = null,
        onSuccess: ((String?) -> Unit)? = null
    )

    fun subscribeToNotifications(
        episode: Episode,
        token: String,
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )

    fun getNotificationSubscriptions(
        onError: onErrorCallBack? = null,
        onSuccess: ((List<String>) -> Unit)? = null
    )

    fun unSubscribeFromNotifications(
        episode: Episode,
        token: String,
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )
}
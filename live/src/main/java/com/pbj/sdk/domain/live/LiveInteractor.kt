package com.pbj.sdk.domain.live

import com.pbj.sdk.domain.live.model.*
import com.pbj.sdk.domain.onErrorCallBack

internal interface LiveInteractor {

    val liveRepository: LiveRepository

    fun getLiveStreams(
        onError: onErrorCallBack? = null,
        onSuccess: ((EpisodeResponse?) -> Unit)? = null
    )

    fun getLiveStreamsSchedule(
        date: String? = null,
        daysAhead: Int? = null,
        size: Int? = null,
        onError: onErrorCallBack? = null,
        onSuccess: ((EpisodeResponse?) -> Unit)? = null
    )

    fun getEpisodesNextPage(
        nextPageUrl: String,
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
        onSuccess: ((BroadcastUrl?) -> Unit)? = null
    )

    fun subscribeToNotificationsFor(
        episode: Episode,
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )

    fun unSubscribeFromNotificationsFor(
        episode: Episode,
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )
}
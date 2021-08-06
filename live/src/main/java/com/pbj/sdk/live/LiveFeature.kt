package com.pbj.sdk.live

import com.pbj.sdk.domain.live.model.BroadcastUrl
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.EpisodeResponse
import com.pbj.sdk.domain.live.model.EpisodeStatusUpdate
import com.pbj.sdk.domain.onErrorCallBack

interface LiveFeature {

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

    fun getCurrentLiveStream(
        onError: onErrorCallBack? = null,
        onSuccess: ((Episode?) -> Unit)? = null
    )

    fun getCurrentLiveStream(
        showId: String,
        onError: onErrorCallBack? = null,
        onSuccess: ((Episode?) -> Unit)? = null
    )

    fun registerForRealTimeLiveStreamUpdates(
        onResult: ((EpisodeStatusUpdate) -> Unit)? = null
    )

    fun leaveRealTimeLiveStreamUpdates()

    fun fetchBroadcastUrl(
        episode: Episode,
        onError: onErrorCallBack? = null,
        onSuccess: ((BroadcastUrl?) -> Unit)? = null
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
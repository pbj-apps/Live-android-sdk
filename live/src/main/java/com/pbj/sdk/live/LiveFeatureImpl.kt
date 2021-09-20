package com.pbj.sdk.live

import com.pbj.sdk.di.LiveKoinComponent
import com.pbj.sdk.domain.live.LiveInteractor
import com.pbj.sdk.domain.live.model.BroadcastUrl
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.EpisodeResponse
import com.pbj.sdk.domain.live.model.EpisodeStatusUpdate
import com.pbj.sdk.domain.onErrorCallBack

internal class LiveFeatureImpl(val liveInteractor: LiveInteractor) : LiveFeature,
    LiveKoinComponent {

    override fun getLiveStreams(
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((EpisodeResponse?) -> Unit)?
    ) {
        liveInteractor.getLiveStreams(onError, onSuccess)
    }

    override fun getEpisodesNextPage(
        nextPageUrl: String,
        onError: onErrorCallBack?,
        onSuccess: ((EpisodeResponse?) -> Unit)?
    ) {
        liveInteractor.getEpisodesNextPage(nextPageUrl, onError, onSuccess)
    }

    override fun getLiveStreamsSchedule(
        date: String?,
        daysAhead: Int?,
        size: Int?,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((EpisodeResponse?) -> Unit)?
    ) {
        liveInteractor.getLiveStreamsSchedule(date, daysAhead, size, onError, onSuccess)
    }

    override fun getCurrentLiveStream(
        onError: onErrorCallBack?,
        onSuccess: ((Episode?) -> Unit)?
    ) {
        liveInteractor.getCurrentEpisode(onError, onSuccess)
    }

    override fun getCurrentLiveStream(
        showId: String,
        onError: onErrorCallBack?,
        onSuccess: ((Episode?) -> Unit)?
    ) {
        liveInteractor.getCurrentEpisode(showId, onError, onSuccess)
    }

    override fun registerForRealTimeLiveStreamUpdates(onResult: ((EpisodeStatusUpdate) -> Unit)?) {
        liveInteractor.registerForRealTimeLiveStreamUpdates(onResult)
    }

    override fun leaveRealTimeLiveStreamUpdates() {
        liveInteractor.leaveRealTimeLiveStreamUpdates()
    }

    override fun fetchBroadcastUrl(
        episode: Episode,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((BroadcastUrl?) -> Unit)?
    ) {
        liveInteractor.getBroadcastUrl(episode, onError, onSuccess)
    }

    override fun subscribeToNotificationsFor(
        episode: Episode,
        onError: ((Throwable) -> Unit)?,
        onSuccess: (() -> Unit)?
    ) {
        liveInteractor.subscribeToNotificationsFor(episode, onError, onSuccess)
    }

    override fun unSubscribeFromNotificationsFor(
        episode: Episode,
        onError: ((Throwable) -> Unit)?,
        onSuccess: (() -> Unit)?
    ) {
        liveInteractor.unSubscribeFromNotificationsFor(episode, onError, onSuccess)
    }
}
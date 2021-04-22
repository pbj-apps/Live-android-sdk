package com.pbj.sdk.live

import com.pbj.sdk.di.LiveKoinComponent
import com.pbj.sdk.domain.live.LiveInteractor
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.EpisodeResponse
import com.pbj.sdk.domain.live.model.EpisodeStatusUpdate
import com.pbj.sdk.domain.onErrorCallBack
import org.koin.core.component.inject

internal class LiveFeatureImpl : LiveFeature, LiveKoinComponent {

    private val liveInteractor: LiveInteractor by inject()

    override fun fetchLiveStreams(
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((EpisodeResponse?) -> Unit)?
    ) {
        liveInteractor.getLiveStreams(onError, onSuccess)
    }

    override fun fetchLiveStreamsSchedule(
        date: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((EpisodeResponse?) -> Unit)?
    ) {
        liveInteractor.getLiveStreamsSchedule(date, onError, onSuccess)
    }

    override fun fetchLiveStreamsSchedule(
        daysAhead: Int,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((EpisodeResponse?) -> Unit)?
    ) {
        liveInteractor.getLiveStreamsSchedule(daysAhead, onError, onSuccess)
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
        onSuccess: ((String?) -> Unit)?
    ) {
        liveInteractor.getBroadcastUrl(episode, onError, onSuccess)
    }

    override fun subscribeToNotifications(
        episode: Episode,
        token: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: (() -> Unit)?
    ) {
        liveInteractor.subscribeToNotifications(episode, token, onError, onSuccess)
    }

    override fun getNotificationSubscriptions(
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((List<String>) -> Unit)?
    ) {
        liveInteractor.getNotificationSubscriptions(onError, onSuccess)
    }

    override fun unSubscribeFromNotifications(
        episode: Episode,
        token: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: (() -> Unit)?
    ) {
        liveInteractor.unSubscribeFromNotifications(episode, token, onError, onSuccess)
    }
}
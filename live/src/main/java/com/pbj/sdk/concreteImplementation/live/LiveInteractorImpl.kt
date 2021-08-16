package com.pbj.sdk.concreteImplementation.live

import com.pbj.sdk.domain.live.LiveInteractor
import com.pbj.sdk.domain.live.LiveRepository
import com.pbj.sdk.domain.live.model.*
import com.pbj.sdk.domain.onErrorCallBack
import com.pbj.sdk.domain.onResult
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import timber.log.Timber

internal class LiveInteractorImpl(
    override val liveRepository: LiveRepository
) : LiveInteractor {

    private var webSocketStatus: WebSocket.Event? = null

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("$throwable")
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + errorHandler)

    init {
        openWebSocketConnection()
    }

    private fun openWebSocketConnection() {
        scope.launch {
            if (webSocketStatus !is WebSocket.Event.OnConnectionOpened<*>) {
                liveRepository.webSocketConnection
                    .collect {
                        webSocketStatus = it

                        if (it is WebSocket.Event.OnConnectionOpened<*>)
                            scope.launch {
                                liveRepository.joinRealTimeLiveStreamUpdates()
                            }
                    }
            }
        }
    }

    override fun getLiveStreams(
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((EpisodeResponse?) -> Unit)?
    ) {
        scope.launch {
            liveRepository.fetchLiveStreams().onResult({
                onError?.invoke(it)
            }) {
                onSuccess?.invoke(it)
            }
        }
    }

    override fun getLiveStreamsSchedule(
        date: String?,
        daysAhead: Int?,
        size: Int?,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((EpisodeResponse?) -> Unit)?
    ) {
        scope.launch {
            liveRepository.fetchLiveStreamsSchedule(date, daysAhead, size).onResult({
                onError?.invoke(it)
            }) {
                onSuccess?.invoke(it)
            }
        }
    }

    override fun getEpisodesNextPage(
        nextPageUrl: String,
        onError: onErrorCallBack?,
        onSuccess: ((EpisodeResponse?) -> Unit)?
    ) {
        scope.launch {
            liveRepository.fetchEpisodesNextPage(nextPageUrl).onResult({
                onError?.invoke(it)
            }) {
                onSuccess?.invoke(it)
            }
        }
    }

    override fun getCurrentEpisode(
        onError: onErrorCallBack?,
        onSuccess: ((Episode?) -> Unit)?
    ) {
        scope.launch {
            liveRepository.fetchCurrentLiveStream().onResult(onError, onSuccess)
        }
    }

    override fun getCurrentEpisode(
        showId: String,
        onError: onErrorCallBack?,
        onSuccess: ((Episode?) -> Unit)?
    ) {
        scope.launch {
            liveRepository.fetchCurrentLiveStream(showId).onResult(onError, onSuccess)
        }
    }

    override fun getShowPublic(
        showId: String,
        onError: onErrorCallBack?,
        onSuccess: ((Show?) -> Unit)?
    ) {
        scope.launch {
            liveRepository.fetchShowPublic(showId).onResult(onError, onSuccess)
        }
    }

    override fun registerForRealTimeLiveStreamUpdates(
        onResult: ((EpisodeStatusUpdate) -> Unit)?
    ) {
        scope.launch {
            liveRepository.registerForRealTimeLiveStreamUpdates()
                .filterNotNull()
                .collect {
                    onResult?.invoke(it)
                }
        }
    }

    override fun leaveRealTimeLiveStreamUpdates() {
        scope.launch {
            liveRepository.leaveRealTimeLiveStreamUpdates()
        }
    }

    override fun getBroadcastUrl(
        episode: Episode,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((BroadcastUrl?) -> Unit)?
    ) {
        scope.launch {
            liveRepository.fetchBroadcastUrl(episode).onResult({
                onError?.invoke(it)
            }) {
                onSuccess?.invoke(it)
            }
        }
    }

    override fun subscribeToNotificationsFor(
        episode: Episode,
        onError: ((Throwable) -> Unit)?,
        onSuccess: (() -> Unit)?
    ) {
        scope.launch {
            liveRepository.subscribeToNotificationsFor(episode).onResult({
                onError?.invoke(it)
            }) {
                onSuccess?.invoke()
            }
        }
    }

    override fun unSubscribeFromNotificationsFor(
        episode: Episode,
        onError: ((Throwable) -> Unit)?,
        onSuccess: (() -> Unit)?
    ) {
        scope.launch {
            liveRepository.unSubscribeFromNotificationsFor(episode).onResult({
                onError?.invoke(it)
            }) {
                onSuccess?.invoke()
            }
        }
    }
}

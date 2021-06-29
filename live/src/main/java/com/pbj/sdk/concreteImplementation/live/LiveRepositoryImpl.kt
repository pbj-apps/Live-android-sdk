package com.pbj.sdk.concreteImplementation.live

import com.pbj.sdk.concreteImplementation.generic.BaseRepository
import com.pbj.sdk.concreteImplementation.generic.mapGenericError
import com.pbj.sdk.concreteImplementation.live.model.JsonWebSocketRequest
import com.pbj.sdk.concreteImplementation.live.model.LiveNotificationSubscription
import com.pbj.sdk.concreteImplementation.live.model.asModel
import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.live.LiveRepository
import com.pbj.sdk.domain.live.model.*
import kotlinx.coroutines.flow.map

internal class LiveRepositoryImpl(
    private val restApi: LiveApi,
    private val socketApi: LiveWebSocketApi
) : BaseRepository(), LiveRepository {

    override suspend fun fetchLiveStreams(): Result<EpisodeResponse> =
        apiCall(
            call = { restApi.fetchLiveStreams() },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        )
        { response ->
            response?.asModel
        }

    override suspend fun fetchLiveStreamsSchedule(date: String): Result<EpisodeResponse> =
        apiCall(
            call = { restApi.fetchLiveStreamsSchedule(date) },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        )
        { response ->
            response?.asModel
        }

    override suspend fun fetchLiveStreamsSchedule(daysAhead: Int): Result<EpisodeResponse> =
        apiCall(
            call = { restApi.fetchLiveStreamsSchedule(daysAhead) },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        )
        { response ->
            response?.asModel
        }

    override suspend fun fetchCurrentLiveStream(): Result<Episode> =
        apiCall(
            call = { restApi.fetchCurrentLiveStream() },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        )
        { response ->
            response?.results?.firstOrNull()?.asModel
        }

    override suspend fun fetchCurrentLiveStream(showId: String): Result<Episode> =
        apiCall(
            call = { restApi.fetchCurrentLiveStream(showId) },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        )
        { response ->
            response?.results?.firstOrNull()?.asModel
        }

    override suspend fun fetchShowPublic(showId: String): Result<Show> =
        apiCall(
            call = { restApi.fetchShowPublic(showId) },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            })
        { response ->
            response?.asModel
        }

    override val webSocketConnection = socketApi.observeWebSocketEvent()

    override suspend fun registerForRealTimeLiveStreamUpdates() =
        socketApi.observeLiveStreamUpdates().map { it.asModel }

    override suspend fun joinRealTimeLiveStreamUpdates() {
        socketApi.subscribe(JsonWebSocketRequest("join-episode-updates"))
    }

    override suspend fun leaveRealTimeLiveStreamUpdates() {
        socketApi.subscribe(JsonWebSocketRequest("leave-episode-updates"))
    }

    override suspend fun fetchBroadcastUrl(episode: Episode): Result<BroadcastUrl> =
        apiCall(
            call = { restApi.fetchBroadcastUrl(episode.id) },
            onApiError = { e, code ->
                mapGenericError(code.code, e)
            }
        ) {
            it?.asModel
        }

    override suspend fun fetchNotificationSubscriptions(): Result<List<String>> =
        apiCall(
            call = { restApi.fetchNotificationSubscriptions() },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        ) { response ->
            response?.results?.mapNotNull { it.topic_id }
        }

    override suspend fun subscribeToNotifications(episode: Episode, token: String): Result<Any> {
        val request = LiveNotificationSubscription(
            "show",
            episode.show?.id,
            listOf(token)
        )

        return apiCall(
            call = { restApi.subscribeToNotification(request) },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        ) { it }
    }

    override suspend fun unSubscribeFromNotifications(
        episode: Episode,
        token: String
    ): Result<Any> {
        val request = LiveNotificationSubscription(
            "show",
            episode.showId,
            listOf(token)
        )
        return apiCall(
            call = { restApi.unsubscribeFromNotifications(request) },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        ) { it }
    }
}

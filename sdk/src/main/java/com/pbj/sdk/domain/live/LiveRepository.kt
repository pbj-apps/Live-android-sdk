package com.pbj.sdk.domain.live

import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.EpisodeResponse
import com.pbj.sdk.domain.live.model.EpisodeStatusUpdate
import com.pbj.sdk.domain.live.model.Show
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

internal interface LiveRepository {

    suspend fun fetchLiveStreams(): Result<EpisodeResponse>
    suspend fun fetchLiveStreamsSchedule(date: String): Result<EpisodeResponse>
    suspend fun fetchLiveStreamsSchedule(daysAhead: Int = 7): Result<EpisodeResponse>

    suspend fun fetchCurrentLiveStream(): Result<Episode>
    suspend fun fetchCurrentLiveStream(showId: String): Result<Episode>
    suspend fun fetchShowPublic(showId: String): Result<Show>

    val webSocketConnection: Flow<WebSocket.Event>
    suspend fun registerForRealTimeLiveStreamUpdates(): Flow<EpisodeStatusUpdate>
    suspend fun joinRealTimeLiveStreamUpdates()
    suspend fun leaveRealTimeLiveStreamUpdates()
    suspend fun fetchBroadcastUrl(episode: Episode): Result<String>

    suspend fun subscribeToNotifications(episode: Episode, token: String): Result<Any>
    suspend fun fetchNotificationSubscriptions(): Result<List<String>>
    suspend fun unSubscribeFromNotifications(episode: Episode, token: String): Result<Any>
}
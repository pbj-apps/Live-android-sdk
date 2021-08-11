package com.pbj.sdk.domain.live

import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.live.model.*
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

internal interface LiveRepository {

    suspend fun fetchLiveStreams(): Result<EpisodeResponse>
    suspend fun fetchLiveStreamsSchedule(date: String?, daysAhead: Int?, size: Int? = 30): Result<EpisodeResponse>
    suspend fun fetchEpisodesNextPage(nextPageUrl: String): Result<EpisodeResponse>

    suspend fun fetchCurrentLiveStream(): Result<Episode>
    suspend fun fetchCurrentLiveStream(showId: String): Result<Episode>
    suspend fun fetchShowPublic(showId: String): Result<Show>

    val webSocketConnection: Flow<WebSocket.Event>
    suspend fun registerForRealTimeLiveStreamUpdates(): Flow<EpisodeStatusUpdate>
    suspend fun joinRealTimeLiveStreamUpdates()
    suspend fun leaveRealTimeLiveStreamUpdates()
    suspend fun fetchBroadcastUrl(episode: Episode): Result<BroadcastUrl>

    suspend fun subscribeToNotificationsFor(episode: Episode): Result<Any>
    suspend fun unSubscribeFromNotificationsFor(episode: Episode): Result<Any>
}
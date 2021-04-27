package com.pbj.sdk.concreteImplementation.live

import com.pbj.sdk.concreteImplementation.live.model.JsonEpisodeStatusUpdate
import com.pbj.sdk.concreteImplementation.live.model.JsonWebSocketRequest
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

internal interface LiveWebSocketApi {
    @Send
    fun subscribe(action: JsonWebSocketRequest)

    @Receive
    fun observeLiveStreamUpdates(): Flow<JsonEpisodeStatusUpdate>

    @Receive
    fun observeWebSocketEvent(): Flow<WebSocket.Event>
}
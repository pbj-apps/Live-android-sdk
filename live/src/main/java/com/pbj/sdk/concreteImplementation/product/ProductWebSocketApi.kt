package com.pbj.sdk.concreteImplementation.product

import com.pbj.sdk.concreteImplementation.live.model.JsonEpisodeStatusUpdate
import com.pbj.sdk.concreteImplementation.live.model.JsonWebSocketProductRequest
import com.pbj.sdk.concreteImplementation.product.model.JsonProductUpdate
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

internal interface ProductWebSocketApi {

    @Send
    fun subscribe(action: JsonWebSocketProductRequest)

    @Receive
    fun observeProductStreamUpdates(): Flow<JsonProductUpdate>

    @Receive
    fun observeWebSocketEvent(): Flow<WebSocket.Event>
}
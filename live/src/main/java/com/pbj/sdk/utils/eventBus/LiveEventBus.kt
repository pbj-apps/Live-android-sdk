package com.pbj.sdk.utils.eventBus

import androidx.annotation.Keep
import kotlinx.coroutines.flow.*

@Keep
object LiveEventBus {

    private val innerEvents = MutableSharedFlow<Any>()
    val events: SharedFlow<Any> = innerEvents.asSharedFlow()

    suspend fun send(event: LiveEvent) {
        innerEvents.emit(event)
    }

    inline fun <reified T : LiveEvent> listen() = events.filter {
        it is T
    }.map {
        it as T
    }
}

@Keep
interface LiveEvent
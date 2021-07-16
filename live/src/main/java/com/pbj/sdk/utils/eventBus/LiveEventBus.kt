package com.pbj.sdk.utils.eventBus

import androidx.annotation.Keep
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance

@Keep
object LiveEventBus {

    private val innerEvents = MutableSharedFlow<Any>()
    val events: SharedFlow<Any> = innerEvents.asSharedFlow()

    suspend fun send(event: LiveEvent) {
        innerEvents.emit(event)
    }

    inline fun <reified T: LiveEvent> listen() = events.filterIsInstance<T>()
}

interface LiveEvent
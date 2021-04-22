package com.pbj.sdk.concreteImplementation.live.model

internal data class JsonLiveNotificationResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<LiveNotificationSubscription>
)
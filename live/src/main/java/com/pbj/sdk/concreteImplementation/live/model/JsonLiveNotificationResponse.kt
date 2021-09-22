package com.pbj.sdk.concreteImplementation.live.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JsonLiveNotificationResponse(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<LiveNotificationSubscription>
)
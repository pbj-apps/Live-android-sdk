package com.pbj.sdk.concreteImplementation.live.model

internal class LiveNotificationSubscription(
    val topic_type: String?,
    val topic_id: String?,
    val device_registration_tokens: List<String>?
)
package com.pbj.sdk.concreteImplementation.live.model

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
internal class LiveNotificationSubscription(
    val topic_type: String?,
    val topic_id: String?,
    val device_registration_tokens: List<String>?
)
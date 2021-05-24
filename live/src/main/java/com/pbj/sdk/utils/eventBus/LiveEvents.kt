package com.pbj.sdk.utils.eventBus

import androidx.annotation.Keep

@Keep
data class LiveNotificationModified(val list: List<String>) : LiveEvent
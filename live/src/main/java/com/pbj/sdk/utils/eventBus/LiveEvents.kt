package com.pbj.sdk.utils.eventBus

import androidx.annotation.Keep

@Keep
class LiveNotificationModified(val episodeId: String, val isReminderSet: Boolean) : LiveEvent
package com.pbj.sdk.notifications

import com.pbj.sdk.domain.live.model.Episode

interface LiveNotificationManager {

    fun init()

    fun toggleReminderFor(
        episode: Episode,
        onError: ((Throwable) -> Unit)?,
        onReminderSet: ((Boolean) -> Unit)? = null
    )
}
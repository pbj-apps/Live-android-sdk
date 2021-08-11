package com.pbj.sdk.notifications

import com.pbj.sdk.domain.live.model.Episode

interface LiveNotificationManager {

    fun init()

    fun toggleReminderFor(
        episode: Episode,
        onReminderSet: ((Boolean) -> Unit)? = null
    )
}
package com.pbj.sdk.notifications

import com.pbj.sdk.domain.live.model.Episode

interface LiveNotificationManager {

    fun init(listener: LiveNotificationListener)

    fun toggleReminderFor(episode: Episode, listener: LiveNotificationListener)

    fun syncSubscriptions(listener: LiveNotificationListener)

    fun isReminderSet(episode: Episode): Boolean

    interface LiveNotificationListener {

        fun onRequestPushNotificationSubscription (episode: Episode, token: String, onSuccess: () -> Unit)

        fun onRequestPushNotificationUnsubscription (episode: Episode, token: String, onSuccess: () -> Unit)

        fun onRequestPushNotificationSubscriptionSync (onSuccess: (List<String>) -> Unit)
    }
}
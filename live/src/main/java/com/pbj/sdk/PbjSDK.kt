package com.pbj.sdk

import android.content.Context
import androidx.annotation.Keep
import com.pbj.sdk.core.ApiEnvironment
import com.pbj.sdk.core.Live
import com.pbj.sdk.core.SdkHolder
import com.pbj.sdk.domain.chat.LiveChatSource
import com.pbj.sdk.guest.GuestFeature
import com.pbj.sdk.live.LiveFeature
import com.pbj.sdk.notifications.LiveNotificationManager
import com.pbj.sdk.user.UserFeature
import com.pbj.sdk.vod.VodFeature

@Keep
interface PbjSDK {

    var liveNotificationManager: LiveNotificationManager?

    var liveChatSource: LiveChatSource?

    val liveFeature: LiveFeature

    val vodFeature: VodFeature

    val userFeature: UserFeature

    val guestFeature: GuestFeature

    companion object {
        fun initialize(
            context: Context,
            apiKey: String,
            environment: ApiEnvironment,
            liveNotificationManager: LiveNotificationManager,
            liveChatSource: LiveChatSource
        ): PbjSDK {
            SdkHolder.instance = Live(context, apiKey, environment, liveNotificationManager, liveChatSource)
            return SdkHolder.instance
        }
    }
}



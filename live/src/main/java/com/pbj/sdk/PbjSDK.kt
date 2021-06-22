package com.pbj.sdk

import android.content.Context
import androidx.annotation.Keep
import com.pbj.sdk.analytics.AnalyticsTracker
import com.pbj.sdk.core.ApiEnvironment
import com.pbj.sdk.core.Live
import com.pbj.sdk.core.SdkHolder
import com.pbj.sdk.domain.chat.LiveChatSource
import com.pbj.sdk.guest.GuestFeature
import com.pbj.sdk.live.LiveFeature
import com.pbj.sdk.notifications.LiveNotificationManager
import com.pbj.sdk.product.ProductFeature
import com.pbj.sdk.user.UserFeature
import com.pbj.sdk.vod.VodFeature

@Keep
interface PbjSDK {

    var liveNotificationManager: LiveNotificationManager?

    var liveChatSource: LiveChatSource?

    var tracker: AnalyticsTracker?

    val liveFeature: LiveFeature

    val productFeature: ProductFeature

    val vodFeature: VodFeature

    val userFeature: UserFeature

    val guestFeature: GuestFeature

    companion object {
        fun initialize(
            context: Context,
            apiKey: String,
            environment: ApiEnvironment,
            liveNotificationManager: LiveNotificationManager,
            liveChatSource: LiveChatSource,
            analyticsTracker: AnalyticsTracker
        ): PbjSDK {
            SdkHolder.instance = Live(context, apiKey, environment, liveNotificationManager, liveChatSource, analyticsTracker)
            return SdkHolder.instance
        }
    }
}



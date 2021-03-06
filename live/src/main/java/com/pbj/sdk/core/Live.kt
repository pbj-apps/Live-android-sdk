package com.pbj.sdk.core

import android.content.Context
import com.pbj.sdk.LiveSDK
import com.pbj.sdk.PbjSDK
import com.pbj.sdk.analytics.AnalyticsTracker
import com.pbj.sdk.di.*
import com.pbj.sdk.domain.chat.LiveChatSource
import com.pbj.sdk.domain.onErrorCallBack
import com.pbj.sdk.guest.GuestFeature
import com.pbj.sdk.live.LiveFeature
import com.pbj.sdk.notifications.LiveNotificationManager
import com.pbj.sdk.organization.OrganizationFeature
import com.pbj.sdk.product.ProductFeature
import com.pbj.sdk.user.UserFeature
import com.pbj.sdk.vod.VodFeature
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.inject
import org.koin.dsl.koinApplication
import timber.log.Timber

internal class Live(
    private val appContext: Context,
    apiKey: String,
    environment: ApiEnvironment,
    override var liveChatSource: LiveChatSource? = null,
    override var tracker: AnalyticsTracker? = null,
) : LiveSDK, PbjSDK, LiveKoinComponent {

    override val organizationFeature: OrganizationFeature by inject()

    override val liveFeature: LiveFeature by inject()

    override val productFeature: ProductFeature by inject()

    override val vodFeature: VodFeature by inject()

    override val userFeature: UserFeature by inject()

    override val guestFeature: GuestFeature by inject()

    override var liveNotificationManager: LiveNotificationManager? = null

    private val sdkModules = mutableListOf(
        DataModule.init(apiKey, environment),
        organizationModule,
        userModule,
        vodModule,
        liveModule,
        productModule,
        trackingModule
    )

    init {
        if (environment.isDebug)
            Timber.plant(Timber.DebugTree())

        if (SdkHolder.isInitialized()) {
            Timber.d("Reset Sdk")
            SdkHolder.instance.close()
            LiveKoinContext.koinApp.modules(sdkModules)
        } else {
            Timber.d("Init SDK as $environment")
            LiveKoinContext.koinApp = koinApplication {

                androidContext(appContext.applicationContext)

                modules(sdkModules)
            }
        }
    }

    override fun addLiveNotificationManager(liveNotificationManager: LiveNotificationManager) {
        this.liveNotificationManager = liveNotificationManager
    }

    override fun isEpisodeLive(onError: onErrorCallBack?, onSuccess: (Boolean) -> Unit) {
        guestFeature.isEpisodeLive(onError, onSuccess)
    }

    override fun isEpisodeLive(
        showId: String,
        onError: onErrorCallBack?,
        onSuccess: (Boolean) -> Unit
    ) {
        guestFeature.isEpisodeLive(showId, onError, onSuccess)
    }

    fun close() {
        LiveKoinContext.koinApp.unloadModules(sdkModules)
    }
}

internal object SdkHolder {
    lateinit var instance: Live

    fun isInitialized() = ::instance.isInitialized
}

package com.pbj.sdk

import android.content.Context
import androidx.annotation.Keep
import com.pbj.sdk.core.ApiEnvironment
import com.pbj.sdk.core.Live
import com.pbj.sdk.core.SdkHolder
import com.pbj.sdk.guest.GuestFeature
import com.pbj.sdk.live.sdkLivePlayer.SDKLivePlayerActivity

@Keep
interface LiveSDK {

    val guestFeature: GuestFeature

    fun startLivePlayer(context: Context, showId: String) {
        SDKLivePlayerActivity.startLivePlayer(context, showId)
    }

    fun startLivePlayer(context: Context) {
        SDKLivePlayerActivity.startLivePlayer(context)
    }

    fun isEpisodeLive(onError: (Throwable) -> Unit, onSuccess: (Boolean) -> Unit)

    fun isEpisodeLive(showId: String, onError: (Throwable) -> Unit, onSuccess: (Boolean) -> Unit)

    companion object {
        fun initialize(context: Context, apiKey: String, environment: ApiEnvironment): LiveSDK {
            SdkHolder.instance = Live(context, apiKey, environment)
            return SdkHolder.instance
        }

        fun close() {
            SdkHolder.instance.close()
        }
    }
}


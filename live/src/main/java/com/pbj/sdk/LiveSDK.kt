package com.pbj.sdk

import android.content.Context
import androidx.annotation.Keep
import com.pbj.sdk.core.ApiEnvironment
import com.pbj.sdk.core.Live
import com.pbj.sdk.core.SdkHolder
import com.pbj.sdk.domain.onErrorCallBack
import com.pbj.sdk.guest.GuestFeature
import com.pbj.sdk.live.sdkLivePlayer.SdkLivePlayerActivity

@Keep
interface LiveSDK {

    val guestFeature: GuestFeature

    fun startLivePlayer(context: Context, showId: String) {
        SdkLivePlayerActivity.startLivePlayer(context, showId)
    }

    fun startLivePlayer(context: Context) {
        SdkLivePlayerActivity.startLivePlayer(context)
    }

    fun isEpisodeLive(onError: onErrorCallBack? = null, onSuccess: (Boolean) -> Unit)

    fun isEpisodeLive(showId: String, onError: onErrorCallBack? = null, onSuccess: (Boolean) -> Unit)

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



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

    /**Get the Intent to start the Live Player in a new activity
     * @param context App context
     * @param showId Id of the show to open
     */
    fun getLivePlayerActivityIntent(context: Context, showId: String) {
        SdkLivePlayerActivity.getLivePlayerActivityIntent(
            context,
            if (showId.isBlank()) showId else null
        )

    }

    /**Get the Intent to start the Live Player in a new activity
     * @param context App context
     */
    fun getLivePlayerActivityIntent(context: Context) {
        SdkLivePlayerActivity.getLivePlayerActivityIntent(context)
    }

    /**Check if an episode is currently live
     * @param onError callback called when an error occurred
     * @param onSuccess callback returning true if an episode is currently live and false if there is none
     */
    fun isEpisodeLive(onError: onErrorCallBack? = null, onSuccess: (Boolean) -> Unit)

    /**Check if a specific episode is currently live
     * @param onError callback called when an error occurred
     * @param onSuccess callback returning true if the episode is currently live and false if not
     */
    fun isEpisodeLive(
        showId: String,
        onError: onErrorCallBack? = null,
        onSuccess: (Boolean) -> Unit
    )

    companion object {
        /**Initialize the Sdk. This should be called as soon as possible
         * @param context Context of the app
         * @param apiKey The Api key required to communicate with the server
         * @param environment Api environment
         */
        fun initialize(context: Context, apiKey: String, environment: ApiEnvironment): LiveSDK {
            SdkHolder.instance = Live(context, apiKey, environment)
            return SdkHolder.instance
        }

        /**Properly close the SDK*/
        fun close() {
            SdkHolder.instance.close()
        }
    }
}



package com.pbj.sdk.guest

import com.pbj.sdk.domain.authentication.GuestInteractor
import com.pbj.sdk.domain.live.LiveInteractor
import com.pbj.sdk.domain.onErrorCallBack
import org.koin.java.KoinJavaComponent

internal class GuestFeatureImpl(
    private val guestInteractor: GuestInteractor
) : GuestFeature {

    private val liveInteractor: LiveInteractor by KoinJavaComponent.inject(LiveInteractor::class.java)

    override fun authenticateAsGuest(
        onError: onErrorCallBack?,
        onSuccess: (() -> Unit)?
    ) {
        guestInteractor.authenticateAsGuest(onError, onSuccess)
    }

    override fun isEpisodeLive(onError: onErrorCallBack?, onSuccess: (Boolean) -> Unit) {
        authenticateAsGuest(onError) {
            liveInteractor.getCurrentEpisode(onError) {
                onSuccess.invoke(it != null)
            }
        }
    }

    override fun isEpisodeLive(
        showId: String,
        onError: onErrorCallBack?,
        onSuccess: (Boolean) -> Unit
    ) {
        authenticateAsGuest(onError) {
            liveInteractor.getCurrentEpisode(showId, onError) {
                onSuccess.invoke(it != null)
            }
        }
    }
}
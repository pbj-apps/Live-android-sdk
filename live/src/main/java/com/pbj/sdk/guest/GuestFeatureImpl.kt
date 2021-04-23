package com.pbj.sdk.guest

import com.pbj.sdk.domain.authentication.GuestInteractor
import com.pbj.sdk.domain.live.LiveInteractor
import com.pbj.sdk.domain.onErrorCallBack

internal class GuestFeatureImpl(
    private val guestInteractor: GuestInteractor,
    private val liveInteractor: LiveInteractor
) : GuestFeature {

    override fun authenticateAsGuest(
        onError: onErrorCallBack?,
        onSuccess: (() -> Unit)?
    ) {
        guestInteractor.authenticateAsGuest(onError, onSuccess)
    }

    override fun isEpisodeLive(onError: (Throwable) -> Unit, onSuccess: (Boolean) -> Unit) {
        authenticateAsGuest(onError) {
            liveInteractor.getCurrentEpisode(onError) {
                onSuccess.invoke(it != null)
            }
        }
    }

    override fun isEpisodeLive(
        showId: String,
        onError: (Throwable) -> Unit,
        onSuccess: (Boolean) -> Unit
    ) {
        authenticateAsGuest(onError) {
            liveInteractor.getCurrentEpisode(showId, onError) {
                onSuccess.invoke(it != null)
            }
        }
    }
}
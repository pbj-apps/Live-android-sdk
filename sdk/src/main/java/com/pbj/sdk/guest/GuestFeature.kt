package com.pbj.sdk.guest

import com.pbj.sdk.domain.onErrorCallBack

interface GuestFeature {

    fun authenticateAsGuest(
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )

    fun isEpisodeLive(onError: (Throwable) -> Unit, onSuccess: (Boolean) -> Unit)

    fun isEpisodeLive(showId: String, onError: (Throwable) -> Unit, onSuccess: (Boolean) -> Unit)
}
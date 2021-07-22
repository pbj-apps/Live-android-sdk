package com.pbj.sdk.guest

import com.pbj.sdk.domain.onErrorCallBack

interface GuestFeature {

    fun authenticateAsGuest(
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )

    fun isEpisodeLive(onError: onErrorCallBack? = null, onSuccess: (Boolean) -> Unit)

    fun isEpisodeLive(showId: String, onError: onErrorCallBack? = null, onSuccess: (Boolean) -> Unit)
}
package com.pbj.sdk.domain.authentication

import com.pbj.sdk.domain.onErrorCallBack

internal interface GuestInteractor {

    val guestRepository: GuestRepository
    val userRepository: UserRepository

    fun authenticateAsGuest(
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )
}
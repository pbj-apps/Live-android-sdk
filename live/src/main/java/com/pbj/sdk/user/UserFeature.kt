package com.pbj.sdk.user

import com.pbj.sdk.domain.authentication.model.RegisterRequest
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.onErrorCallBack
import java.io.File

interface UserFeature {

    fun login(
        email: String,
        password: String,
        onError: onErrorCallBack? = null,
        onSuccess: ((User) -> Unit)? = null
    )

    fun register(
        registerRequest: RegisterRequest,
        onError: onErrorCallBack? = null,
        onSuccess: ((User) -> Unit)? = null
    )

    fun getUser(
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    fun getLocalUser(
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    fun updateUser(
        firstname: String,
        lastname: String,
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    fun uploadProfilePicture(
        image: File,
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    fun changePassword(
        currentPassword: String, newPassword: String,
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )

    fun logout(onError: onErrorCallBack? = null, onSuccess: (() -> Unit)? = null)

    fun isUserLoggedIn(onResult: ((Boolean) -> Unit)? = null)

    fun isLoggedInAsGuest(onError: onErrorCallBack? = null, onSuccess: ((Boolean?) -> Unit)? = null)

    fun saveIsLoggedInAsGuest(
        isGuest: Boolean,
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )

    fun updateDeviceRegistrationToken(
        token: String,
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )
}
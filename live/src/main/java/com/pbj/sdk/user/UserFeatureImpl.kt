package com.pbj.sdk.user

import android.net.Uri
import com.pbj.sdk.di.LiveKoinComponent
import com.pbj.sdk.domain.authentication.UserInteractor
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.onErrorCallBack
import java.io.File

internal class UserFeatureImpl(private val userInteractor: UserInteractor) : UserFeature, LiveKoinComponent {

    override fun login(
        email: String,
        password: String,
        onError: onErrorCallBack?,
        onSuccess: ((User) -> Unit)?
    ) {
        userInteractor.login(email, password, onError, onSuccess)
    }

    override fun getUser(
        onError: onErrorCallBack?,
        onSuccess: ((User?) -> Unit)?
    ) {
        userInteractor.getUser(onError, onSuccess)
    }

    override fun getLocalUser(
        onError: onErrorCallBack?,
        onSuccess: ((User?) -> Unit)?
    ) {
        userInteractor.getLocalUser(onError, onSuccess)
    }

    override fun updateUser(
        firstname: String,
        lastname: String,
        onError: onErrorCallBack?,
        onSuccess: ((User?) -> Unit)?
    ) {
        userInteractor.updateUser(firstname, lastname, onError, onSuccess)
    }

    override fun uploadProfilePicture(
        image: File,
        uri: Uri,
        onError: onErrorCallBack?,
        onSuccess: ((User?) -> Unit)?
    ) {
        userInteractor.uploadProfilePicture(image, uri, onError, onSuccess)
    }

    override fun changePassword(
        currentPassword: String, newPassword: String,
        onError: onErrorCallBack?,
        onSuccess: (() -> Unit)?
    ) {
        userInteractor.changePassword(currentPassword, newPassword, onError, onSuccess)
    }

    override fun logout() {
        userInteractor.logout()
    }

    override fun isUserLoggedIn(onResult: ((Boolean) -> Unit)?) =
        userInteractor.isUserLoggedIn(onResult)

    override fun isLoggedInAsGuest(
        onError: onErrorCallBack?,
        onSuccess: ((Boolean?) -> Unit)?
    ) {
        userInteractor.isLoggedInAsGuest(onError, onSuccess)
    }

    override fun saveIsLoggedInAsGuest(
        isGuest: Boolean,
        onError: onErrorCallBack?,
        onSuccess: (() -> Unit)?
    ) {
        userInteractor.saveIsLoggedInAsGuest(isGuest, onError, onSuccess)
    }
}
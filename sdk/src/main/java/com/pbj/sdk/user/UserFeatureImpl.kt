package com.pbj.sdk.user

import android.net.Uri
import com.pbj.sdk.di.LiveKoinComponent
import com.pbj.sdk.domain.authentication.UserInteractor
import com.pbj.sdk.domain.authentication.model.User
import java.io.File

internal class UserFeatureImpl(private val userInteractor: UserInteractor) : UserFeature, LiveKoinComponent {

    override fun login(
        email: String,
        password: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((User) -> Unit)?
    ) {
        userInteractor.login(email, password, onError, onSuccess)
    }

    override fun getUser(
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((User?) -> Unit)?
    ) {
        userInteractor.getUser(onError, onSuccess)
    }

    override fun getLocalUser(
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((User?) -> Unit)?
    ) {
        userInteractor.getLocalUser(onError, onSuccess)
    }

    override fun updateUser(
        firstname: String,
        lastname: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((User?) -> Unit)?
    ) {
        userInteractor.updateUser(firstname, lastname, onError, onSuccess)
    }

    override fun uploadProfilePicture(
        image: File,
        uri: Uri,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((User?) -> Unit)?
    ) {
        userInteractor.uploadProfilePicture(image, uri, onError, onSuccess)
    }

    override fun changePassword(
        currentPassword: String, newPassword: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: (() -> Unit)?
    ) {
        userInteractor.changePassword(currentPassword, newPassword, onError, onSuccess)
    }

    override fun logout() {
        userInteractor.logout()
    }

    override fun isUserLoggedIn(onResult: ((Boolean) -> Unit)?) =
        userInteractor.isUserLoggedIn(onResult)
}
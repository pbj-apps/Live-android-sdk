package com.pbj.sdk.domain.authentication

import android.net.Uri
import com.pbj.sdk.domain.authentication.model.User
import java.io.File

internal interface UserInteractor {

    val userRepository: UserRepository

    fun login(
        email: String,
        password: String,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((User) -> Unit)? = null
    )

    fun getUser(
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    fun getLocalUser(
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    fun updateUser(
        firstname: String,
        lastname: String,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    fun uploadProfilePicture(
        image: File,
        uri: Uri,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    fun changePassword(
        currentPassword: String, newPassword: String,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: (() -> Unit)? = null
    )

    fun logout()

    fun isUserLoggedIn(onResult: ((Boolean) -> Unit)? = null)
}
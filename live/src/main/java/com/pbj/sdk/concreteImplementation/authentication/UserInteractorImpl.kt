package com.pbj.sdk.concreteImplementation.authentication

import android.net.Uri
import com.pbj.sdk.domain.*
import com.pbj.sdk.domain.authentication.UserInteractor
import com.pbj.sdk.domain.authentication.UserRepository
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.vod.model.ProfileImage
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.File

internal class UserInteractorImpl(
    override val userRepository: UserRepository
) : UserInteractor {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e( "$throwable")
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + errorHandler)

    override fun login(
        email: String,
        password: String,
        onError: onErrorCallBack?,
        onSuccess: ((User) -> Unit)?
    ) {
        var user: User? = null

        scope.launch {
            userRepository.login(email, password).onResult(onError = {
                onError?.invoke(it)
            }) {
                user = it
            }

            user?.let {
                userRepository.apply {
                    saveUser(it)
                    saveToken(it.authToken)
                    saveIsLoggedInAsGuest(false)
                }
                onSuccess?.invoke(it)
            }
        }
    }

    override fun getUser(
        onError: onErrorCallBack?,
        onSuccess: ((User?) -> Unit)?
    ) {

        scope.launch {
            val user: User? = userRepository.getUser()
                .onError(onError).onSuccess(onSuccess).successResult

            user?.let {
                saveUser(it)
            }
        }
    }

    override fun getLocalUser(onError: onErrorCallBack?, onSuccess: ((User?) -> Unit)?) {
        scope.launch {
            userRepository.getLocallySavedUser().onResult(onError) {
                onSuccess?.invoke(it)
            }
        }
    }

    override fun updateUser(
        firstname: String,
        lastname: String,
        onError: onErrorCallBack?,
        onSuccess: ((User?) -> Unit)?
    ) {
        scope.launch {
            var user: User? = null

            userRepository.updateUser(firstname, lastname)
                .onResult(
                    onError = { onError?.invoke(it)
                    }
                )

            getUser {
                user = it?.copy(firstname = firstname, lastname = lastname)
            }

            user?.let {
                saveUser(it)
            }

            onSuccess?.invoke(user)
        }
    }

    override fun uploadProfilePicture(
        image: File,
        uri: Uri,
        onError: onErrorCallBack?,
        onSuccess: ((User?) -> Unit)?
    ) {
        scope.launch {
            var profileImage: ProfileImage? = null

            userRepository.uploadProfilePicture(image, uri).onResult(
                {
                    onError?.invoke(it)
                }
            ) {
                profileImage = it
            }

            userRepository.getUser().onResult {
                it?.copy(avatarUrl = profileImage?.small)
                onSuccess?.invoke(it)
            }
        }
    }

    override fun changePassword(
        currentPassword: String,
        newPassword: String,
        onError: onErrorCallBack?,
        onSuccess: (() -> Unit)?
    ) {
        scope.launch {
            userRepository.changePassword(currentPassword, newPassword)
                .onResult({
                    onError?.invoke(it)
                }) {
                    onSuccess?.invoke()
                }
        }
    }

    override fun logout() {
        scope.launch {
            userRepository.logout()
        }
    }

    private fun saveUser(user: User) {
        scope.launch {
            userRepository.saveUser(user)
        }
    }

    override fun isUserLoggedIn(onResult: ((Boolean) -> Unit)?) {
        scope.launch {
            userRepository.getUserToken()
                .onResult({
                    Timber.e(it)
                }) {
                    onResult?.invoke(!it.isNullOrBlank())
                }
        }
    }

    override fun isLoggedInAsGuest(
        onError: onErrorCallBack?,
        onSuccess: ((Boolean?) -> Unit)?
    ) {
        scope.launch {
            userRepository.isLoggedInAsGuest()
                .onResult({
                    Timber.e(it)
                    onError?.invoke(it)
                }) {
                    onSuccess?.invoke(it)
                }
        }
    }

    override fun saveIsLoggedInAsGuest(
        isGuest: Boolean,
        onError: onErrorCallBack?,
        onSuccess: (() -> Unit)?
    ) {
        scope.launch {
            userRepository.saveIsLoggedInAsGuest(isGuest)
                .onResult({
                    Timber.e(it)
                    onError?.invoke(it)
                }) {
                    onSuccess?.invoke()
                }
        }
    }
}

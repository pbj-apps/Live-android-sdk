package com.pbj.sdk.concreteImplementation.authentication

import android.util.Log
import com.pbj.sdk.concreteImplementation.authentication.model.asJson
import com.pbj.sdk.domain.*
import com.pbj.sdk.domain.authentication.UserInteractor
import com.pbj.sdk.domain.authentication.UserRepository
import com.pbj.sdk.domain.authentication.model.RegisterRequest
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.vod.model.ProfileImage
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.File

internal class UserInteractorImpl(
    private val userRepository: UserRepository
) : UserInteractor {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(Log.getStackTraceString(throwable))
        throw throwable
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
                saveAuthenticatedUser(it)
                onSuccess?.invoke(it)
            }
        }
    }

    override fun register(
        registerRequest: RegisterRequest,
        onError: onErrorCallBack?,
        onSuccess: ((User) -> Unit)?
    ) {
        var user: User? = null

        scope.launch {
            userRepository.register(registerRequest.asJson).onResult(onError = {
                onError?.invoke(it)
            }) {
                user = it
            }

            user?.let {
                saveAuthenticatedUser(it)
                onSuccess?.invoke(it)
            }
        }
    }

    private suspend fun saveAuthenticatedUser(user: User?) {
        user?.let {
            userRepository.apply {
                saveUser(it)
                saveToken(it.authToken)
                saveIsLoggedInAsGuest(false)
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
                    onError = {
                        onError?.invoke(it)
                    }
                )

            getUser {
                user = it?.copy(firstname = firstname, lastname = lastname)

                user?.let { newUser ->
                    saveUser(newUser)
                }

                onSuccess?.invoke(user)
            }
        }
    }

    override fun uploadProfilePicture(
        image: File,
        onError: onErrorCallBack?,
        onSuccess: ((User?) -> Unit)?
    ) {
        scope.launch {
            var profileImage: ProfileImage? = null

            userRepository.uploadProfilePicture(image).onResult(
                {
                    onError?.invoke(it)
                }
            ) {
                profileImage = it
            }

            userRepository.getUser().onResult {
                onSuccess?.invoke(
                    it?.copy(avatarUrl = profileImage?.small)
                )
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

    override fun logout(onError: onErrorCallBack?, onSuccess: (() -> Unit)?) {
        scope.launch {
            userRepository.logout().onResult({
                onError?.invoke(it)
            }, {
                onSuccess?.invoke()
            })
        }
    }

    private fun saveUser(user: User) {
        scope.launch {
            userRepository.saveUser(user)
        }
    }

    override fun isLoggedIn(onResult: ((Boolean) -> Unit)?) {
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

    override fun updateDeviceRegistrationToken(
        token: String,
        onError: onErrorCallBack?,
        onSuccess: (() -> Unit)?
    ) {
        scope.launch {
            userRepository.updateDeviceRegistrationToken(token).onResult({
                Timber.e(it)
                onError?.invoke(it)
            }) {
                onSuccess?.invoke()
            }
        }
    }
}

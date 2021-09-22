package com.pbj.sdk.concreteImplementation.authentication

import android.util.Log
import com.pbj.sdk.di.LiveKoinComponent
import com.pbj.sdk.domain.authentication.GuestInteractor
import com.pbj.sdk.domain.authentication.GuestRepository
import com.pbj.sdk.domain.authentication.UserRepository
import com.pbj.sdk.domain.onError
import com.pbj.sdk.domain.onErrorCallBack
import com.pbj.sdk.domain.onSuccess
import com.pbj.sdk.domain.successResult
import kotlinx.coroutines.*
import timber.log.Timber

internal class GuestInteractorImpl(override val guestRepository: GuestRepository,
                                   override val userRepository: UserRepository
) : GuestInteractor, LiveKoinComponent {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(Log.getStackTraceString(throwable))
        throw throwable
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + errorHandler)

    override fun authenticateAsGuest(onError: onErrorCallBack?, onSuccess: (() -> Unit)?) {
        scope.launch {
            val guestToken = guestRepository.fetchGuestToken()
                .onError(onError)
                .onSuccess {
                    onSuccess?.invoke()
                }.successResult

            userRepository.apply {
                guestToken?.authToken?.let {
                    saveToken(it)
                    saveIsLoggedInAsGuest(true)
                }
            }
        }
    }
}
package com.pbj.sdk.concreteImplementation.storage

import android.content.Context
import com.pbj.sdk.domain.authentication.model.User
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal class PBJPreferences(context: Context, moshi: Moshi) : AbstractPreferences(context, moshi) {

    init {
        updateIsGuestOnTokenChange()
    }

    var user: User?
        get() = retrieveObject(USER)
        set(value) {
            try {
                saveObject(USER, value)
            } catch (e: Throwable) {
                throw  Exception(e)
            }
        }

    var userToken: String?
        get() =
            retrieve(TOKEN)
        set(value) {
            save(TOKEN, value)
        }

    var isLoggedInAsGuest: Boolean?
        get() =
            retrieve(LOGGED_IN_AS_GUEST, true)
        private set(value) {
            save(LOGGED_IN_AS_GUEST, value)
        }

    private fun updateIsGuestOnTokenChange() {
        observeKey<String?>(TOKEN, null)
            ?.map {
                user == null && userToken != null
            }
            ?.onEach { isGuest ->
                isLoggedInAsGuest = isGuest
            }
            ?.distinctUntilChanged()
    }

    companion object {
        private const val USER = "USER"
        private const val TOKEN = "TOKEN"
        private const val LOGGED_IN_AS_GUEST = "LOGGED_IN_AS_GUEST"
    }
}
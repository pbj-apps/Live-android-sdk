package com.pbj.sdk.concreteImplementation.storage

import android.content.Context
import com.pbj.sdk.domain.authentication.model.User
import com.squareup.moshi.Moshi

internal class PBJPreferences(context: Context, moshi: Moshi) : AbstractPreferences(context, moshi) {

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

    companion object {
        private const val USER = "USER"
        private const val TOKEN = "TOKEN"
    }
}
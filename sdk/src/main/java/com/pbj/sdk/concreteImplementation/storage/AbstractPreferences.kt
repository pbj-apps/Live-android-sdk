package com.pbj.sdk.concreteImplementation.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import timber.log.Timber

internal abstract class AbstractPreferences(val context: Context, protected val moshi: Moshi) {


    var preferences: SharedPreferences? = null

    private val prefName: String
        get() = PREF_NAME + context.packageName

    init {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    protected fun <T> save(key: String, value: T?) {
        preferences?.edit {
            value?.let {
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Boolean -> putBoolean(key, value)
                    else -> Timber.e("Type Not supported, add this type in ${this@AbstractPreferences}")
                }

                Timber.d( "Saved $key: $value")
            } ?: run {
                remove(key)
                Timber.d( "Deleted $key")
            }
        }
    }

    inline fun <reified T> retrieve(key: String, defaultValue: T? = null): T? {

        val value: T? = when (T::class) {
            String::class -> preferences?.getString(key, defaultValue as String?) as T?
            Boolean::class -> preferences?.getBoolean(key, defaultValue as Boolean) as T?
            Int::class -> preferences?.getInt(key, defaultValue as Int) as T?
            Long::class -> preferences?.getLong(key, defaultValue as Long) as T?
            else -> null
        }

        Timber.d( "$key: ${value.toString()}")
        return value
    }

    protected inline fun <reified T> saveObject(key: String, item: T?) {
        try {
            val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
            val json = adapter.toJson(item)
            Timber.d(json)
            save(key, json)
        } catch (e: Throwable) {
            throw Exception(e)
        }
    }

    protected inline fun <reified T> retrieveObject(key: String, defaultValue: T? = null): T? {
        try {
            val json: String? = retrieve(key)
            return json?.let {
                Timber.d(json)
                val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
                adapter.fromJson(json) ?: defaultValue
            }
        } catch (e: Throwable) {
            throw Exception(e)
        }
    }

    companion object {
        const val PREF_NAME = "PBJ"
    }
}
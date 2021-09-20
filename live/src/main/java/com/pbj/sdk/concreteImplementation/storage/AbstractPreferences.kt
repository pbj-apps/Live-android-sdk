package com.pbj.sdk.concreteImplementation.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

internal abstract class AbstractPreferences(val context: Context, protected val moshi: Moshi) {

    var preferences: SharedPreferences? = null

    private val prefName: String
        get() = PREF_NAME + context.packageName

    init {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    protected inline fun <reified T> save(key: String, value: T?) {
        preferences?.edit {
            value?.let {
                setItem(key, it)
                Timber.d("Saved $key: $value")
            } ?: run {
                remove(key)
                Timber.d("Deleted $key")
            }
        }
    }

    inline fun <reified T> retrieve(key: String, defaultValue: T? = null): T? {
        val value = preferences?.getItem(key, defaultValue)
        Timber.d("$key: ${value.toString()}")
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

    inline fun <reified T> observeKey(key: String, defaultValue: T): Flow<T?>? =
        preferences?.observeKey(key, defaultValue)

    companion object {
        const val PREF_NAME = "PBJ"
    }
}

inline fun <reified T> SharedPreferences.observeKey(key: String, default: T?): Flow<T?> {
    val flow = MutableStateFlow(getItem(key, default))

    val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, k ->
        if (key == k) {
            flow.value = getItem(key, default)
        }
    }
    registerOnSharedPreferenceChangeListener(listener)

    return flow.onCompletion {
        unregisterOnSharedPreferenceChangeListener(listener)
    }
}

inline fun <reified T> SharedPreferences.getItem(key: String, default: T?): T? {
    @Suppress("UNCHECKED_CAST")
    return when (T::class) {
        String::class -> getString(key, default as String?) as T?
        Boolean::class -> getBoolean(key, default as Boolean) as T?
        Int::class -> getInt(key, default as Int) as T?
        Long::class -> getLong(key, default as Long) as T?
        Float::class -> getFloat(key, default as Float) as T?
        else -> throw IllegalArgumentException("Can't get item for key $key. Generic type not handle ${T::class.java.name}")
    }
}

inline fun <reified T> SharedPreferences.Editor.setItem(key: String, value: T?) {
    @Suppress("UNCHECKED_CAST")
    when (value) {
        is String -> putString(key, value)
        is Boolean -> putBoolean(key, value)
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        else -> throw IllegalArgumentException("Can't set item for key $key. Generic type not handle ${T::class.java.name}")
    }
}
package com.pbj.sdk.utils

import android.util.Log
import timber.log.Timber

fun Throwable.log() {
    Timber.e(Log.getStackTraceString(this))
}
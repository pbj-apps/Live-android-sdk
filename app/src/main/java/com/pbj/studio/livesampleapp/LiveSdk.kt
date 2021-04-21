package com.pbj.studio.livesampleapp

import android.content.Context
import com.pbj.sdk.LiveSDK
import com.pbj.sdk.core.ApiEnvironment

object Live {

    fun init(context: Context, apiKey: String, environment: ApiEnvironment) {
        instance = LiveSDK.initialize(context, apiKey, environment)
    }

    lateinit var instance: LiveSDK
}
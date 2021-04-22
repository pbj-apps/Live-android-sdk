package com.pbj.sdk.concreteImplementation.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.time.ZonedDateTime

internal class TimeZoneInterceptor : Interceptor {

    companion object {
        private const val HEADER = "Tz-Info"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()

        val timeZoneId = ZonedDateTime.now().zone.id

        newBuilder.addHeader(HEADER, timeZoneId)

        return chain.proceed(newBuilder.build())
    }
}
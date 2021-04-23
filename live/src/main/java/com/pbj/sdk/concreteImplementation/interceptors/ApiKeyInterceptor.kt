package com.pbj.sdk.concreteImplementation.interceptors

import okhttp3.Interceptor
import okhttp3.Response

internal class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    companion object {
        private const val HEADER = "X-api-key"

    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()

        newBuilder.addHeader(HEADER, apiKey)

        return chain.proceed(newBuilder.build())
    }
}
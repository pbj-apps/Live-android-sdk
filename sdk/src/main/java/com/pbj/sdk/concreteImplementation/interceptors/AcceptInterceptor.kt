package com.pbj.sdk.concreteImplementation.interceptors

import okhttp3.Interceptor
import okhttp3.Response

internal class AcceptInterceptor : Interceptor {

    companion object {
        private const val HEADER = "Accept"
        private const val ACCEPT = "application/vnd.pbj+json; version=1.0"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()

        newBuilder.addHeader(HEADER, ACCEPT)

        return chain.proceed(newBuilder.build())
    }
}
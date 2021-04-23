package com.pbj.sdk.concreteImplementation.interceptors

import com.pbj.sdk.concreteImplementation.storage.PBJPreferences
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthorizationInterceptor(private val userSharedPreferences: PBJPreferences) :
    Interceptor {

    companion object {
        private const val HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()

        val token: String? = userSharedPreferences.userToken
        val bearerToken = "Bearer $token"

        if (!token.isNullOrBlank()) {
            newBuilder.addHeader(HEADER, bearerToken)
        }

        return chain.proceed(newBuilder.build())
    }
}
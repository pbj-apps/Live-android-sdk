package com.pbj.sdk.concreteImplementation.authentication

import com.pbj.sdk.concreteImplementation.live.model.JSONGuestAuthResponse
import retrofit2.Response
import retrofit2.http.POST

internal interface GuestApi {

    @POST("auth/guest/session")
    suspend fun fetchGuestToken(): Response<JSONGuestAuthResponse>
}
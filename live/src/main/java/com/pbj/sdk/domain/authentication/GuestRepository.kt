package com.pbj.sdk.domain.authentication

import com.pbj.sdk.concreteImplementation.authentication.model.GuestAuthResponse
import com.pbj.sdk.domain.Result

internal interface GuestRepository {
    suspend fun fetchGuestToken(): Result<GuestAuthResponse>
}
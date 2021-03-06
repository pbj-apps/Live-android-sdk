package com.pbj.sdk.concreteImplementation.authentication

import com.pbj.sdk.concreteImplementation.authentication.model.GuestAuthResponse
import com.pbj.sdk.concreteImplementation.generic.BaseRepository
import com.pbj.sdk.concreteImplementation.generic.mapGenericError
import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.authentication.GuestRepository
import com.pbj.sdk.domain.live.model.asModel
import com.squareup.moshi.Moshi

internal class GuestRepositoryImpl(private val api: GuestApi,
                                   override val moshi: Moshi
) : BaseRepository(moshi), GuestRepository {

    override suspend fun fetchGuestToken(): Result<GuestAuthResponse> =
        apiCall(call = { api.fetchGuestToken() },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        ) {
            it?.asModel
        }
}
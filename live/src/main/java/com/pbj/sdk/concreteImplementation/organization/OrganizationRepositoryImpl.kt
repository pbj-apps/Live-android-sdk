package com.pbj.sdk.concreteImplementation.organization

import com.pbj.sdk.concreteImplementation.generic.BaseRepository
import com.pbj.sdk.concreteImplementation.generic.mapGenericError
import com.pbj.sdk.concreteImplementation.organization.model.asModel
import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.organization.OrganizationRepository
import com.pbj.sdk.domain.organization.model.OrganizationConfig
import com.squareup.moshi.Moshi

internal class OrganizationRepositoryImpl(val api: OrganizationApi,
                                          override val moshi: Moshi
) : BaseRepository(moshi), OrganizationRepository {

    override suspend fun fetchOrganizationConfig(): Result<OrganizationConfig> =
        apiCall(
            call = { api.fetchOrganizationConfig() },
            onApiError = { e, code ->
                mapGenericError(code.code, e)
            },
            onSuccess = {
                it?.asModel
            }
        )
}
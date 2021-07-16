package com.pbj.sdk.concreteImplementation.organization

import com.pbj.sdk.concreteImplementation.organization.model.JsonOrganizationConfig
import retrofit2.Response
import retrofit2.http.GET

internal interface OrganizationApi {

    @GET("/api/organizations/current")
    suspend fun fetchOrganizationConfig(): Response<JsonOrganizationConfig>
}
package com.pbj.sdk.domain.organization

import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.organization.model.OrganizationConfig

internal interface OrganizationRepository  {

    suspend fun fetchOrganizationConfig(): Result<OrganizationConfig>
}
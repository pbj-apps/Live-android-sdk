package com.pbj.sdk.domain.organization

import com.pbj.sdk.domain.onErrorCallBack
import com.pbj.sdk.domain.organization.model.OrganizationConfig

internal interface OrganizationInteractor {
    val organizationRepository: OrganizationRepository

    fun getConfig(
        onError: onErrorCallBack? = null,
        onSuccess: ((OrganizationConfig?) -> Unit)? = null
    )
}
package com.pbj.sdk.organization

import com.pbj.sdk.domain.onErrorCallBack
import com.pbj.sdk.domain.organization.model.OrganizationConfig

interface OrganizationFeature {

    fun getConfig(
        onError: onErrorCallBack? = null,
        onSuccess: ((OrganizationConfig?) -> Unit)? = null
    )
}
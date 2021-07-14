package com.pbj.sdk.organization

import com.pbj.sdk.domain.onErrorCallBack
import com.pbj.sdk.domain.organization.OrganizationInteractor
import com.pbj.sdk.domain.organization.model.OrganizationConfig

internal class OrganizationFeatureImpl(
    private val organizationInteractor: OrganizationInteractor
) : OrganizationFeature {

    override fun getConfig(onError: onErrorCallBack?, onSuccess: ((OrganizationConfig?) -> Unit)?) {
        organizationInteractor.getConfig(onError, onSuccess)
    }
}
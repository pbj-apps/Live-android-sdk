package com.pbj.sdk.concreteImplementation.organization

import com.pbj.sdk.domain.onErrorCallBack
import com.pbj.sdk.domain.onResult
import com.pbj.sdk.domain.organization.OrganizationInteractor
import com.pbj.sdk.domain.organization.OrganizationRepository
import com.pbj.sdk.domain.organization.model.OrganizationConfig
import com.pbj.sdk.utils.log
import kotlinx.coroutines.*

internal class OrganizationInteractorImpl(override val organizationRepository: OrganizationRepository) :
    OrganizationInteractor {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.log()
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + errorHandler)

    override fun getConfig(
        onError: onErrorCallBack?,
        onSuccess: ((OrganizationConfig?) -> Unit)?
    ) {
        scope.launch {
            organizationRepository.fetchOrganizationConfig().onResult(onError, onSuccess)
        }
    }
}
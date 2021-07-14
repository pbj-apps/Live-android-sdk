package com.pbj.sdk.di

import com.pbj.sdk.concreteImplementation.organization.OrganizationApi
import com.pbj.sdk.concreteImplementation.organization.OrganizationInteractorImpl
import com.pbj.sdk.concreteImplementation.organization.OrganizationRepositoryImpl
import com.pbj.sdk.domain.organization.OrganizationInteractor
import com.pbj.sdk.domain.organization.OrganizationRepository
import com.pbj.sdk.organization.OrganizationFeature
import com.pbj.sdk.organization.OrganizationFeatureImpl
import org.koin.dsl.module

internal val organizationModule = module {

    single { provideOrganizationRepository(get()) }

    single { provideOrganizationInteractor(get()) }

    single { provideOrganizationFeature(get()) }
}

internal fun provideOrganizationRepository(api: OrganizationApi): OrganizationRepository =
    OrganizationRepositoryImpl(api)

internal fun provideOrganizationInteractor(organizationRepository: OrganizationRepository): OrganizationInteractor =
    OrganizationInteractorImpl(organizationRepository)

internal fun provideOrganizationFeature(
    organizationInteractor: OrganizationInteractor
): OrganizationFeature = OrganizationFeatureImpl(organizationInteractor)
package com.pbj.sdk.di

import com.pbj.sdk.concreteImplementation.authentication.*
import com.pbj.sdk.concreteImplementation.storage.PBJPreferences
import com.pbj.sdk.domain.authentication.GuestInteractor
import com.pbj.sdk.domain.authentication.GuestRepository
import com.pbj.sdk.domain.authentication.UserInteractor
import com.pbj.sdk.domain.authentication.UserRepository
import com.pbj.sdk.guest.GuestFeature
import com.pbj.sdk.guest.GuestFeatureImpl
import com.pbj.sdk.user.UserFeature
import com.pbj.sdk.user.UserFeatureImpl
import com.squareup.moshi.Moshi
import org.koin.dsl.module

internal val userModule = module {

    single { provideUserRepository(get(), get(), get()) }

    single { provideUserInteractor(get()) }

    single { provideUserFeature(get()) }

    single { provideGuestRepository(get(), get()) }

    single { provideGuestInteractor(get(), get()) }

    single { provideGuestFeature(get()) }
}

internal fun provideUserRepository(
    api: UserApi,
    userSharedPreferences: PBJPreferences,
    moshi: Moshi
): UserRepository = UserRepositoryImpl(api, userSharedPreferences, moshi)

internal fun provideUserInteractor(
    userRepository: UserRepository
): UserInteractor = UserInteractorImpl(userRepository)

internal fun provideUserFeature(
    userInteractor: UserInteractor
): UserFeature = UserFeatureImpl(userInteractor)

internal fun provideGuestRepository(
    api: GuestApi,
    moshi: Moshi
): GuestRepository = GuestRepositoryImpl(api, moshi)

internal fun provideGuestInteractor(
    guestRepository: GuestRepository,
    userRepository: UserRepository
): GuestInteractor {
    return GuestInteractorImpl(guestRepository, userRepository)
}

internal fun provideGuestFeature(
    guestInteractor: GuestInteractor
): GuestFeature = GuestFeatureImpl(guestInteractor)
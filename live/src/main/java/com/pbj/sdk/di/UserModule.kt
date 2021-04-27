package com.pbj.sdk.di

import com.pbj.sdk.concreteImplementation.authentication.*
import com.pbj.sdk.concreteImplementation.storage.PBJPreferences
import com.pbj.sdk.domain.authentication.GuestInteractor
import com.pbj.sdk.domain.authentication.GuestRepository
import com.pbj.sdk.domain.authentication.UserInteractor
import com.pbj.sdk.domain.authentication.UserRepository
import com.pbj.sdk.domain.live.LiveInteractor
import com.pbj.sdk.guest.GuestFeature
import com.pbj.sdk.guest.GuestFeatureImpl
import com.pbj.sdk.user.UserFeature
import com.pbj.sdk.user.UserFeatureImpl
import org.koin.dsl.module

internal val userModule = module {

    single { provideUserRepository(get(), get()) }

    single { provideUserInteractor(get()) }

    single { provideUserFeature(get()) }

    single { provideGuestRepository(get()) }

    single { provideGuestInteractor(get(), get()) }

    single { provideGuestFeature(get(), get()) }
}

internal fun provideUserRepository(
    api: UserApi,
    userSharedPreferences: PBJPreferences
): UserRepository = UserRepositoryImpl(api, userSharedPreferences)

internal fun provideUserInteractor(
    userRepository: UserRepository
): UserInteractor = UserInteractorImpl(userRepository)

internal fun provideUserFeature(
    userInteractor: UserInteractor
): UserFeature = UserFeatureImpl(userInteractor)

internal fun provideGuestRepository(
    api: GuestApi,
): GuestRepository = GuestRepositoryImpl(api)

internal fun provideGuestInteractor(
    guestRepository: GuestRepository,
    userRepository: UserRepository
): GuestInteractor {
    return GuestInteractorImpl(guestRepository, userRepository)
}

internal fun provideGuestFeature(
    guestInteractor: GuestInteractor,
    liveInteractor: LiveInteractor
): GuestFeature = GuestFeatureImpl(guestInteractor, liveInteractor)
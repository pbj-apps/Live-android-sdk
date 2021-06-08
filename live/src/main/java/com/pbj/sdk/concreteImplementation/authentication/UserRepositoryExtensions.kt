package com.pbj.sdk.concreteImplementation.authentication

import com.pbj.sdk.concreteImplementation.generic.JsonGenericError
import com.pbj.sdk.domain.authentication.RegisterError

internal fun UserRepositoryImpl.mapRegisterError(
    errorCode: Int,
    error: JsonGenericError?
): RegisterError =
    when (errorCode) {
        400 -> RegisterError.ValidationError(null)
        else -> RegisterError.Unknown(error?.errors?.firstOrNull()?.message)
    }
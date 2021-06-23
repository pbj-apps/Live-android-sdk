package com.pbj.sdk.concreteImplementation.generic

import com.pbj.sdk.domain.GenericError
import com.pbj.sdk.domain.authentication.LoginError

internal fun ErrorCode.mapLoginError(): LoginError =
    when (code) {
        400 -> LoginError.WrongCredentials(
            if (message.isNullOrBlank())
                "Invalid username/password. Please try again"
            else message
        )
        else -> LoginError.Unknown(message)
    }

internal fun BaseRepository.mapGenericError(errorCode: Int, error: JsonGenericError?): Throwable {
    val message = error?.errors?.firstOrNull()?.message
    return when {
        errorCode == 403 || error?.error_type == "NotAuthenticated" ->
            GenericError.NoPermission(message)
        else -> GenericError.Unknown(message)
    }
}

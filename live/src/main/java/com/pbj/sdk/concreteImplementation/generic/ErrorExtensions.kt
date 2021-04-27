package com.pbj.sdk.concreteImplementation.generic

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
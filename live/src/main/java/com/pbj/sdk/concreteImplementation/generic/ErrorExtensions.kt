package com.pbj.sdk.concreteImplementation.generic

import com.pbj.sdk.domain.authentication.LoginError
import com.pbj.sdk.domain.authentication.PushNotificationError

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
        errorCode == 401 || error?.error_type == "NotAuthenticated" ->
            GenericError.NoPermission(message)
        else -> GenericError.Unknown(message)
    }
}

internal fun BaseRepository.mapPushNotificationError(
    errorCode: Int,
    error: JsonGenericError?
): Throwable {
    val message = error?.errors?.firstOrNull()?.message
    return when {
        errorCode == 400 || error?.error_type == "ValidationError" ->
            PushNotificationError.ValidationError(message)
        else -> PushNotificationError.Unknown(message)
    }
}


sealed class GenericError : Throwable() {
    data class NoNetwork(override val message: String? = null) : GenericError()
    data class EmptyResponse(override val message: String? = null) : GenericError()
    data class NoPermission(override val message: String? = null) : GenericError()
    data class ErrorParsing(override val message: String? = null) : GenericError()
    data class Unknown(override val message: String? = null) : GenericError()
}

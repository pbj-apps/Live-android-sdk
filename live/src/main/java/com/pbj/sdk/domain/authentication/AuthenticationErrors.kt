package com.pbj.sdk.domain.authentication

sealed class LoginError : Throwable() {
    data class WrongCredentials(override val message: String?) : LoginError()
    data class Unknown(override val message: String?) : LoginError()
}

sealed class RegisterError : Throwable() {
    data class ValidationError(override val message: String?) : RegisterError()
    data class Unknown(override val message: String?) : RegisterError()
}

sealed class PasswordChangeError : Throwable() {
    data class PasswordNotMatching(override val message: String?) : PasswordChangeError()
    data class PasswordTooCommon(override val message: String?) : PasswordChangeError()
    data class Unknown(override val message: String?) : PasswordChangeError()
}

sealed class PushNotificationError : Throwable() {
    data class ValidationError(override val message: String?) : PushNotificationError()
    data class Unknown(override val message: String?) : PushNotificationError()
}
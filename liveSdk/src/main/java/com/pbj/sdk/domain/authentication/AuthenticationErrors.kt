package com.pbj.sdk.domain.authentication

sealed class LoginError : Throwable() {
    data class WrongCredentials(override val message: String?) : LoginError()
    data class Unknown(override val message: String?) : LoginError()
}
sealed class PasswordChangeError : Throwable() {
    data class PASSWORDS_DONT_MATCH(override val message: String?) : PasswordChangeError()
    data class PASSWORD_TOO_COMMON(override val message: String?) : PasswordChangeError()
    data class Unknown(override val message: String?) : PasswordChangeError()
}
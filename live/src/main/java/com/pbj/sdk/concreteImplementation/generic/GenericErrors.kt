package com.pbj.sdk.concreteImplementation.generic

sealed class GenericError : Throwable() {
    data class NoNetwork(override val message: String? = null) : GenericError()
    data class EmptyResponse(override val message: String? = null) : GenericError()
    data class NoPermission(override val message: String? = null) : GenericError()
    data class ErrorParsing(override val message: String? = null) : GenericError()
    data class Unknown(override val message: String? = null) : GenericError()
}
package com.pbj.sdk.domain

sealed class Result<Value> {
    class Success<Value>(val value: Value? = null) : Result<Value>()
    class Error<Value>(val error: Throwable) : Result<Value>()
}

fun <Value> Result<Value>.onResult(onError: onErrorCallBack? = null, onSuccess: ((Value?) -> Unit)? = null) {
    when (this) {
        is Result.Success -> onSuccess?.invoke(value)
        is Result.Error -> onError?.invoke(error)
    }
}

fun <Value> Result<Value>.onError(onError: onErrorCallBack? = null): Result<Value> {
    when (this) {
        is Result.Error -> onError?.invoke(error)
    }
    return this
}

fun <Value> Result<Value>.onSuccess(onSuccess: ((Value?) -> Unit)? = null): Result<Value> {
    when (this) {
        is Result.Success -> onSuccess?.invoke(value)
    }
    return this
}

val <Value> Result<Value>.successResult: Value?
    get() = when (this) {
            is Result.Success -> value
            else -> null
        }

val <Value> Result<Value>.errorResult: Throwable?
    get() = when (this) {
            is Result.Error -> error
            else -> null
        }


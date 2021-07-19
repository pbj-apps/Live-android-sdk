package com.pbj.sdk.concreteImplementation.generic

import com.pbj.sdk.domain.GenericError
import com.pbj.sdk.domain.Result
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import retrofit2.Response
import timber.log.Timber
import java.net.UnknownHostException

internal typealias onError = (JsonGenericError?, code: ErrorCode) -> Throwable

internal abstract class BaseRepository(open val moshi: Moshi) {

    protected suspend fun <Json, T : Any> apiCall(
        call: suspend () -> Response<Json>,
        onApiError: onError,
        onSuccess: (Json?) -> T?
    ): Result<T> {

        val response: Response<Json>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            val error = mapGenericError(t)
            Timber.e(error.localizedMessage)
            return Result.Error(error)
        }

        return if (!response.isSuccessful) {

            val errorResult =
                moshi.adapter(JsonGenericError::class.java).fromJson(response.errorBody()?.string())

            @Suppress("BlockingMethodInNonBlockingContext")
            Result.Error(
                onApiError.invoke(
                    errorResult,
                    ErrorCode(response.code(), response.message())
                )
            )
        } else {
            Timber.d(response.body().toString())
            return Result.Success(
                onSuccess.invoke(response.body())
            )
        }
    }

    private fun mapGenericError(throwable: Throwable): GenericError {
        return when (throwable) {
            is UnknownHostException -> GenericError.NoNetwork(throwable.localizedMessage)
            is JsonDataException -> GenericError.ErrorParsing(throwable.localizedMessage)
            else -> GenericError.Unknown(throwable.localizedMessage)
        }
    }
}

data class ErrorCode(val code: Int, val message: String?)
package com.pbj.sdk.concreteImplementation.authentication

import android.net.Uri
import com.pbj.sdk.concreteImplementation.authentication.model.ChangePasswordRequest
import com.pbj.sdk.concreteImplementation.authentication.model.JsonLoginRequest
import com.pbj.sdk.concreteImplementation.authentication.model.JsonRegisterRequest
import com.pbj.sdk.concreteImplementation.authentication.model.UpdateProfileRequest
import com.pbj.sdk.concreteImplementation.authentication.model.extensions.asModel
import com.pbj.sdk.concreteImplementation.generic.BaseRepository
import com.pbj.sdk.concreteImplementation.generic.mapGenericError
import com.pbj.sdk.concreteImplementation.generic.mapLoginError
import com.pbj.sdk.concreteImplementation.storage.PBJPreferences
import com.pbj.sdk.concreteImplementation.vod.model.asModel
import com.pbj.sdk.domain.GenericError
import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.authentication.UserRepository
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.vod.model.ProfileImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

internal class UserRepositoryImpl(
    private val api: UserApi,
    private val preferences: PBJPreferences
) : BaseRepository(), UserRepository {

    override suspend fun login(email: String, password: String): Result<User> =
        apiCall(call = {
            api.loginUser(JsonLoginRequest(email, password))
        },
            onApiError = { _, code ->
                code.mapLoginError()
            }) {
            it?.asModel
        }

    override suspend fun register(registerRequest: JsonRegisterRequest): Result<User> =
        apiCall(call = {
            api.registerUser(registerRequest)
        },
            onApiError = { e, code -> mapRegisterError(code.code, e) }) {
            it?.asModel
        }


    override suspend fun getUser(): Result<User> =
        apiCall(call = {
            api.getUser()
        },
            onApiError = { e, code ->
                mapGenericError(code.code, e)
            }
        ) {
            it?.asModel
        }

    override suspend fun getLocallySavedUser(): Result<User> {
        return Result.Success(preferences.user)
    }

    override suspend fun updateUser(firstname: String, lastname: String): Result<Any> =
        apiCall(call = {
            api.updateUser(
                UpdateProfileRequest(firstname, lastname)
            )
        },
            onApiError = { _, code -> code.mapLoginError() }) {
            Any()
        }

    override suspend fun uploadProfilePicture(image: File, uri: Uri): Result<ProfileImage> {

        val body = image.asRequestBody("image".toMediaTypeOrNull())

        val requestFile = MultipartBody.Part.createFormData(
            "image",
            "profile_picture.jpg",
            body
        )

        val result: Result<ProfileImage> = apiCall(call = {
            api.uploadProfilePicture(
                requestFile
            )
        }, onApiError = { e, code ->
            mapGenericError(code.code, e)
        }
        ) {
            it?.asModel
        }

        return when (result) {
            is Result.Error -> result
            is Result.Success -> result.value?.let { updateUserProfileImage(it) }
                ?: Result.Error(GenericError.Unknown())
        }
    }

    private suspend fun updateUserProfileImage(profileImage: ProfileImage): Result<ProfileImage> =
        apiCall(call = {
            api.updateUser(
                mapOf("profile_image" to profileImage.id)
            )
        }, onApiError = { e, code ->
            mapGenericError(code.code, e)
        }
        ) {
            profileImage
        }

    override suspend fun changePassword(currentPassword: String, newPassword: String): Result<Any> =
        apiCall(call = {
            api.changePassword(
                ChangePasswordRequest(currentPassword, newPassword)
            )
        }, onApiError = { e, code ->
            mapGenericError(code.code, e)
        }
        ) {
            Any()
        }

    override suspend fun logout(): Result<Any> = removeCurrentUser()

    override suspend fun saveUser(user: User): Result<Any> {
        preferences.user = user
        return Result.Success()
    }

    override suspend fun getUserToken(): Result<String> = Result.Success(preferences.userToken)

    private suspend fun removeCurrentUser(): Result<Any> {
        preferences.user = null
        return removeToken()
    }

    override suspend fun saveToken(token: String?): Result<Any> {
        preferences.userToken = token
        return Result.Success()
    }

    override suspend fun removeToken(): Result<Any> {
        preferences.userToken = null
        return Result.Success()
    }

    override suspend fun isLoggedInAsGuest(): Result<Boolean> =
        Result.Success(preferences.isLoggedInAsGuest)

    override suspend fun saveIsLoggedInAsGuest(isGuest: Boolean): Result<Any> {
        preferences.isLoggedInAsGuest = isGuest
        return Result.Success()
    }
}

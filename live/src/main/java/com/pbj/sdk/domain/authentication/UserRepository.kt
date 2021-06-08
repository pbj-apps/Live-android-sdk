package com.pbj.sdk.domain.authentication

import android.net.Uri
import com.pbj.sdk.concreteImplementation.authentication.model.JsonRegisterRequest
import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.vod.model.ProfileImage
import java.io.File

internal interface UserRepository {

    suspend fun login(email: String, password: String): Result<User>

    suspend fun register(registerRequest: JsonRegisterRequest): Result<User>

    suspend fun getUser(): Result<User>

    suspend fun getLocallySavedUser(): Result<User>

    suspend fun updateUser(firstname: String, lastname: String): Result<Any>

    suspend fun uploadProfilePicture(image: File, uri: Uri): Result<ProfileImage>

    suspend fun changePassword(currentPassword: String, newPassword: String): Result<Any>

    suspend fun logout(): Result<Any>

    suspend fun saveUser(user: User): Result<Any>

    suspend fun getUserToken(): Result<String>

    suspend fun removeUser(user: User): Result<Any>

    suspend fun saveToken(token: String?): Result<Any>

    suspend fun removeToken(): Result<Any>

    suspend fun isLoggedInAsGuest(): Result<Boolean>

    suspend fun saveIsLoggedInAsGuest(isGuest: Boolean): Result<Any>
}
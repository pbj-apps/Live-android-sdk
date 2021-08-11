package com.pbj.sdk.concreteImplementation.authentication

import com.pbj.sdk.concreteImplementation.authentication.model.*
import com.pbj.sdk.concreteImplementation.vod.model.JsonProfileImage
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

internal interface UserApi {

    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: JsonLoginRequest): Response<JsonUser>

    @POST("auth/register")
    suspend fun registerUser(@Body registerRequest: JsonRegisterRequest): Response<JsonUser>

    @POST("auth/password-change")
    suspend fun changePassword(@Body updateRequest: ChangePasswordRequest): Response<Any>

    @GET("me")
    suspend fun getUser(): Response<JsonUser>

    @PATCH("me")
    suspend fun updateUser(@Body updateRequest: UpdateProfileRequest): Response<Any>

    @PATCH("me")
    suspend fun updateUser(@Body params: Map<String, String>): Response<Any>

    @Multipart
    @POST("profile-images")
    suspend fun uploadProfilePicture(
        @Part imageFile: MultipartBody.Part
    ): Response<JsonProfileImage>

    @POST("device-registration-tokens")
    suspend fun updateDeviceRegistrationToken(@Body deviceToken: DeviceToken): Response<Any>
}
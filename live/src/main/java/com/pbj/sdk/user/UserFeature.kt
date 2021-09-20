package com.pbj.sdk.user

import com.pbj.sdk.domain.authentication.model.RegisterRequest
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.onErrorCallBack
import java.io.File

interface UserFeature {

    /**Sign in as a user with an email and password
     * @param email a string representing the email of the user
     * @param password a string representing the password related to a user account
     * @param onError callback called when an error occurred during login
     * @param onSuccess callback called when the user logged in successfully
     */
    fun login(
        email: String,
        password: String,
        onError: onErrorCallBack? = null,
        onSuccess: ((User) -> Unit)? = null
    )

    /**Create a new user account
     * @param registerRequest an object containing the necessary data to create a new user account
     * @param onError callback called when an error occurred during sign up
     * @param onSuccess callback called when the user signed up successfully
     */
    fun register(
        registerRequest: RegisterRequest,
        onError: onErrorCallBack? = null,
        onSuccess: ((User) -> Unit)? = null
    )

    /**Get current user data
     * @param onError callback called when an error occurred
     * @param onSuccess callback called when data have been retrieved successfully
     */
    fun getUser(
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )


    /**Get current user saved locally in the phone
     * @param onError callback called when an error occurred
     * @param onSuccess callback called when data have been retrieved successfully
     */
    fun getLocalUser(
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    fun updateUser(
        firstname: String,
        lastname: String,
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    fun uploadProfilePicture(
        image: File,
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    fun changePassword(
        currentPassword: String, newPassword: String,
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )

    fun logout(onError: onErrorCallBack? = null, onSuccess: (() -> Unit)? = null)

    fun isUserLoggedIn(onResult: ((Boolean) -> Unit)? = null)

    fun isLoggedInAsGuest(onError: onErrorCallBack? = null, onSuccess: ((Boolean?) -> Unit)? = null)

    fun saveIsLoggedInAsGuest(
        isGuest: Boolean,
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )

    fun updateDeviceRegistrationToken(
        token: String,
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )
}
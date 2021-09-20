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
     * @param onSuccess callback returning the current user. It is called when the user is logged in successfully
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
     * @param onSuccess callback returning the current user. It is called when the user signed up successfully
     */
    fun register(
        registerRequest: RegisterRequest,
        onError: onErrorCallBack? = null,
        onSuccess: ((User) -> Unit)? = null
    )

    /**Get current user data
     * @param onError callback called when an error occurred
     * @param onSuccess callback returning the current user. It is called when data have been retrieved successfully
     */
    fun getUser(
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )


    /**Get current user saved locally in the phone
     * @param onError callback called when an error occurred
     * @param onSuccess callback returning the current user. It is called when data have been retrieved successfully
     */
    fun getLocalUser(
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    /**Update current user
     * @param firstname new firstname for the current user
     * @param lastname new lastname for the current user
     * @param onError callback called when an error occurred
     * @param onSuccess callback returning the updated user. It is called when user has been updated successfully
     */
    fun updateUser(
        firstname: String,
        lastname: String,
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    /**Update current user's profile picture
     * @param image file representing user's new profile picture
     * @param onError callback called when an error occurred
     * @param onSuccess callback returning the updated user. It is called when the profile picture of the user has been updated successfully
     */
    fun uploadProfilePicture(
        image: File,
        onError: onErrorCallBack? = null,
        onSuccess: ((User?) -> Unit)? = null
    )

    /**Update current user's password
     * @param currentPassword the current password of the user
     * @param newPassword the new password of the user
     * @param onError callback called when an error occurred
     * @param onSuccess callback called when user's password has been updated successfully
     */
    fun changePassword(
        currentPassword: String,
        newPassword: String,
        onError: onErrorCallBack? = null,
        onSuccess: (() -> Unit)? = null
    )

    /**logout current user
     * @param onError callback called when an error occurred
     * @param onSuccess callback called when current user has been logged out successfully
     */
    fun logout(onError: onErrorCallBack? = null, onSuccess: (() -> Unit)? = null)

    /** Check if user is logged in or not
     * @param onError callback called when an error occurred
     * @param onSuccess callback returning true if the user is logged in or false if not
     */
    fun isUserLoggedIn(onResult: ((Boolean) -> Unit)? = null)

    /** Check if user is a guest or not
     * @param onError callback called when an error occurred
     * @param onSuccess callback returning true if the current user is a guest or false if not
     */
    fun isLoggedInAsGuest(onError: onErrorCallBack? = null, onSuccess: ((Boolean?) -> Unit)? = null)

    /** Check if user is a guest or not
     * @param onError callback called when an error occurred
     * @param onSuccess callback returning true if the current user is a guest or false if not
     */
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
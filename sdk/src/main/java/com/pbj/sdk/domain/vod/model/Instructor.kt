package com.pbj.sdk.domain.vod.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Instructor(
    val id: String,
    val firstName: String?,
    val lastName: String?,
    val username: String?,
    val email: String?,
    val isContentProgrammer: Boolean,
    val isInstructor: Boolean,
    val isStaff: Boolean,
    val isSurveyAttempted: Boolean,
    val isVerified: Boolean,
    val profileImage: ProfileImage?
) : Parcelable
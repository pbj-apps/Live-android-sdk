package com.pbj.sdk.domain.live.model

import android.os.Parcelable
import com.pbj.sdk.domain.vod.model.ProfileImage
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Streamer(
    val id: String,
    val dob: LocalDate?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val username: String?,
    val isContentProgrammer: Boolean?,
    val isInstructor: Boolean?,
    val isStaff: Boolean?,
    val isSurvey_attempted: Boolean?,
    val isVerified: Boolean?,
    val profileImage: ProfileImage?
) : Parcelable
package com.pbj.sdk.domain.live.model

import android.os.Parcelable
import com.pbj.sdk.domain.vod.model.ProfileImage
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Streamer(
    val id: String,
    val dob: LocalDate? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val username: String? = null,
    val isContentProgrammer: Boolean? = null,
    val isInstructor: Boolean? = null,
    val isStaff: Boolean? = null,
    val isSurveyAttempted: Boolean? = null,
    val isVerified: Boolean? = null,
    val profileImage: ProfileImage? = null
) : Parcelable
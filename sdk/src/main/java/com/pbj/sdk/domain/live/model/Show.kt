package com.pbj.sdk.domain.live.model

import android.os.Parcelable
import com.pbj.sdk.domain.vod.model.Asset
import com.pbj.sdk.domain.vod.model.Instructor
import kotlinx.parcelize.Parcelize

@Parcelize
data class Show(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val waitingRoomDescription: String? = null,
    val duration: Int? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val instructors: List<Instructor>? = null,
    val previewAsset: Asset? = null,
    val previewImageUrl: String? = null,
    val previewUrlType: String? = null,
    val previewVideoUrl: String? = null,
    val rruleSchedule: String? = null
) : Parcelable
package com.pbj.sdk.domain.live.model

import android.os.Parcelable
import com.pbj.sdk.domain.vod.model.VodVideo
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime

@Parcelize
data class Episode(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val chatMode: ChatMode? = null,
    val duration: Int? = null,
    val startDate: OffsetDateTime? = null,
    val endDate: OffsetDateTime? = null,
    val show: Show? = null,
    val status: EpisodeStatus,
    val streamer: Streamer? = null,
    val video: VodVideo? = null
) : Parcelable
package com.pbj.sdk.domain.vod.model

val Asset.previewImage: String?
    get() = image?.medium

val VodVideo.durationInMinutes: Int
    get() {
        duration?.let {
            val videoDuration = duration % 60
            return if (videoDuration < 60) 1 else videoDuration
        } ?: return 0
    }
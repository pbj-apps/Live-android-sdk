package com.pbj.sdk.domain.vod.model

val Asset.previewImage: String?
    get() = image?.medium

val VodVideo.durationInMinutes: Int
    get() {
        duration?.let {
            return duration % 60
        } ?: return 0
    }
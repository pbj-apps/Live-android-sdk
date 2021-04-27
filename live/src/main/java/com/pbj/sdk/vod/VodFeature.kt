package com.pbj.sdk.vod

import com.pbj.sdk.domain.vod.model.VodCategory
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo

interface VodFeature {

    companion object {
        internal val instance by lazy { VodFeatureImpl() }
    }

    fun getVodCategories(
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((List<VodCategory>) -> Unit)? = null
    )

    fun getPlaylist(
        id: String, onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((VodPlaylist?) -> Unit)? = null
    )

    fun getVideo(
        id: String, onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((VodVideo?) -> Unit)? = null
    )
}
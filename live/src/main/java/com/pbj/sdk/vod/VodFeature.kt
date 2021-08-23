package com.pbj.sdk.vod

import com.pbj.sdk.domain.vod.model.VodCategoriesResponse
import com.pbj.sdk.domain.vod.model.VodItemResponse
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo

interface VodFeature {

    fun getVodCategories(
        itemsPerCategory: Int = 10,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((VodCategoriesResponse?) -> Unit)? = null
    )

    fun getNextVodCategoryPage(
        url: String,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((VodCategoriesResponse?) -> Unit)? = null
    )

    fun getPlaylist(
        id: String, onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((VodPlaylist?) -> Unit)? = null
    )

    fun getVideo(
        id: String, onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((VodVideo?) -> Unit)? = null
    )

    fun searchVideos(
        title: String,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((VodItemResponse?) -> Unit)? = null
    )
}
package com.pbj.sdk.domain.vod

import com.pbj.sdk.domain.vod.model.VodCategoriesResponse
import com.pbj.sdk.domain.vod.model.VodItemResponse
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo

internal interface VodInteractor {

    fun getVodCategories(
        itemsPerCategory: Int,
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
package com.pbj.sdk.domain.vod

import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.vod.model.VodCategoriesResponse
import com.pbj.sdk.domain.vod.model.VodItemResponse
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo

internal interface VodRepository {

    suspend fun fetchVodCategories(itemsPerCategory: Int): Result<VodCategoriesResponse>
    suspend fun fetchNextVodCategoryPage(url: String): Result<VodCategoriesResponse>
    suspend fun getPlaylist(id: String): Result<VodPlaylist>
    suspend fun getVideo(id: String): Result<VodVideo>
    suspend fun searchForVideos(title: String): Result<VodItemResponse>
}
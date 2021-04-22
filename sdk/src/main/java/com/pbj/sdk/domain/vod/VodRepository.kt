package com.pbj.sdk.domain.vod

import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.vod.model.VodCategory
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo

internal interface VodRepository {

    suspend fun getVodCategories(): Result<List<VodCategory>>
    suspend fun getPlaylist(id: String): Result<VodPlaylist>
    suspend fun getVideo(id: String): Result<VodVideo>

}
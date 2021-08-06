package com.pbj.sdk.concreteImplementation.vod

import com.pbj.sdk.concreteImplementation.generic.BaseRepository
import com.pbj.sdk.concreteImplementation.generic.mapGenericError
import com.pbj.sdk.concreteImplementation.vod.model.asModel
import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.vod.VodRepository
import com.pbj.sdk.domain.vod.model.VodCategoriesResponse
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo
import com.squareup.moshi.Moshi

internal class VodRepositoryImpl(private val api: VodApi,
                                 override val moshi: Moshi
) : BaseRepository(moshi), VodRepository {

    override suspend fun fetchVodCategories(itemsPerCategory: Int): Result<VodCategoriesResponse> =
        apiCall(
            call = { api.getCategories(itemsPerCategory) },
            onApiError = { e, code ->
                mapGenericError(code.code, e)
            }
        )
        { result ->
            result?.asModel
        }

    override suspend fun fetchNextVodCategoryPage(url: String): Result<VodCategoriesResponse> =
        apiCall(
            call = { api.fetchNextVodPage(url) },
            onApiError = { e, code ->
                mapGenericError(code.code, e)
            }
        )
        { result ->
            result?.asModel
        }

    override suspend fun getPlaylist(id: String): Result<VodPlaylist> =
        apiCall(
            call = { api.getPlaylist(id) },
            onApiError = { e, code ->
                mapGenericError(code.code, e)
            }
        ) {
            it?.asModel
        }

    override suspend fun getVideo(id: String): Result<VodVideo> =
        apiCall(
            call = { api.getVideo(id) },
            onApiError = { e, code ->
                mapGenericError(code.code, e)
            }
        ) {
            it?.asModel
        }
}
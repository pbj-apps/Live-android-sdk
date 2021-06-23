package com.pbj.sdk.concreteImplementation.vod

import com.pbj.sdk.concreteImplementation.generic.BaseRepository
import com.pbj.sdk.concreteImplementation.generic.mapGenericError
import com.pbj.sdk.concreteImplementation.vod.model.asModel
import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.vod.VodRepository
import com.pbj.sdk.domain.vod.model.VodCategory
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo

internal class VodRepositoryImpl(private val api: VodApi) : BaseRepository(), VodRepository {

    override suspend fun getVodCategories(): Result<List<VodCategory>> =
        apiCall(
            call = { api.getCategories() },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        )
        { page ->
            page?.results?.map { it.asModel }
        }

    override suspend fun getPlaylist(id: String): Result<VodPlaylist> =
        apiCall(
            call = { api.getPlaylist(id) },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        ) {
            it?.asModel
        }

    override suspend fun getVideo(id: String): Result<VodVideo> =
        apiCall(
            call = { api.getVideo(id) },
            onApiError = { e, code ->
                mapGenericError(
                    code.code,
                    e
                )
            }
        ) {
            it?.asModel
        }
}
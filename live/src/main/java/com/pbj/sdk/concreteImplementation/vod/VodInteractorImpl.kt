package com.pbj.sdk.concreteImplementation.vod

import android.util.Log
import com.pbj.sdk.domain.onResult
import com.pbj.sdk.domain.vod.VodInteractor
import com.pbj.sdk.domain.vod.VodRepository
import com.pbj.sdk.domain.vod.model.VodCategoriesResponse
import com.pbj.sdk.domain.vod.model.VodItemResponse
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo
import kotlinx.coroutines.*
import timber.log.Timber

internal class VodInteractorImpl(private val vodRepository: VodRepository) : VodInteractor {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(Log.getStackTraceString(throwable))
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + errorHandler)

    override fun getVodCategories(
        itemsPerCategory: Int,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((VodCategoriesResponse?) -> Unit)?
    ) {
        scope.launch {
            vodRepository.fetchVodCategories(itemsPerCategory).onResult(
                {
                    onError?.invoke(it)
                }
            ) {
                onSuccess?.invoke(it)
            }
        }
    }

    override fun getNextVodCategoryPage(
        url: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((VodCategoriesResponse?) -> Unit)?
    ) {
        scope.launch {
            vodRepository.fetchNextVodCategoryPage(url).onResult(onError) {
                onSuccess?.invoke(it)
            }
        }
    }

    override fun getPlaylist(
        id: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((VodPlaylist?) -> Unit)?
    ) {
        scope.launch {
            vodRepository.getPlaylist(id).onResult({
                onError?.invoke(it)
            }) {
                onSuccess?.invoke(it)
            }
        }
    }

    override fun getVideo(
        id: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((VodVideo?) -> Unit)?
    ) {
        scope.launch {
            vodRepository.getVideo(id).onResult({
                onError?.invoke(it)
            }) {
                onSuccess?.invoke(it)
            }
        }
    }

    override fun searchVideos(
        title: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((VodItemResponse?) -> Unit)?
    ) {
        scope.launch {
            vodRepository.searchForVideos(title).onResult({
                onError?.invoke(it)
            }) {
                onSuccess?.invoke(it)
            }
        }
    }
}
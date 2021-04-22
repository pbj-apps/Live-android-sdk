package com.pbj.sdk.concreteImplementation.vod

import com.pbj.sdk.domain.onResult
import com.pbj.sdk.domain.vod.VodInteractor
import com.pbj.sdk.domain.vod.VodRepository
import com.pbj.sdk.domain.vod.model.VodCategory
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo
import kotlinx.coroutines.*
import timber.log.Timber

internal class VodInteractorImpl(override val vodRepository: VodRepository): VodInteractor  {

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("$throwable")
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + errorHandler)

    override fun getVodCategories(
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((List<VodCategory>) -> Unit)?
    ) {
        scope.launch {
            vodRepository.getVodCategories().onResult(
                {
                    onError?.invoke(it)
                }
            ) {
                onSuccess?.invoke(it ?: listOf())
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

}
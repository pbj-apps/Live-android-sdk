package com.pbj.sdk.vod

import com.pbj.sdk.di.LiveKoinComponent
import com.pbj.sdk.domain.vod.VodInteractor
import com.pbj.sdk.domain.vod.model.VodCategoriesResponse
import com.pbj.sdk.domain.vod.model.VodItemResponse
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo
import org.koin.core.component.inject

internal class VodFeatureImpl : VodFeature, LiveKoinComponent {

    private val vodInteractor: VodInteractor by inject()

    override fun getVodCategories(
        itemsPerCategory: Int,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((VodCategoriesResponse?) -> Unit)?
    ) {
        vodInteractor.getVodCategories(itemsPerCategory, onError, onSuccess)
    }

    override fun getNextVodCategoryPage(
        url: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((VodCategoriesResponse?) -> Unit)?
    ) {
        vodInteractor.getNextVodCategoryPage(url, onError, onSuccess)
    }

    override fun getPlaylist(
        id: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((VodPlaylist?) -> Unit)?
    ) {
        vodInteractor.getPlaylist(id, onError, onSuccess)
    }

    override fun getVideo(
        id: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((VodVideo?) -> Unit)?
    ) {
        vodInteractor.getVideo(id, onError, onSuccess)
    }

    override fun searchVideos(
        title: String,
        onError: ((Throwable) -> Unit)?,
        onSuccess: ((VodItemResponse?) -> Unit)?
    ) {
        vodInteractor.searchVideos(title, onError, onSuccess)
    }
}
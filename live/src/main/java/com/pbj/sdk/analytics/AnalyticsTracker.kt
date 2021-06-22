package com.pbj.sdk.analytics

import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.domain.vod.model.VodCategory
import com.pbj.sdk.domain.vod.model.VodPlaylist
import com.pbj.sdk.domain.vod.model.VodVideo

interface AnalyticsTracker {

    fun logLiveClassStarts(episode: Episode)

    fun logLiveClassExit(episode: Episode)

    fun logVodClassStarts(video: VodVideo, categoryList: List<VodCategory>?)

    fun logVodClassExit(video: VodVideo, categoryList: List<VodCategory>?)

    fun logVodDetailsViewed(video: VodVideo, playlist: List<VodPlaylist>?, categoryList: List<VodCategory>?)

    fun logPlaylistViewed(playlist: VodPlaylist)

    fun logCategorySelected(category: VodCategory)

    fun logChatMessageSent(episode: Episode)

    fun logFeaturedProductClicked(product: Product)
}
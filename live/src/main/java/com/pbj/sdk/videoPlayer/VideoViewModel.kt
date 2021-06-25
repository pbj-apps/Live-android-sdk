package com.pbj.sdk.videoPlayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import com.pbj.sdk.analytics.AnalyticsTracker
import com.pbj.sdk.di.LiveKoinComponent
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.live.livePlayer.ProductTimeCodes
import org.koin.core.component.inject

class VideoViewModel : ViewModel(), LiveKoinComponent {

    private val tracker: AnalyticsTracker by inject()

    var videoPlayer: SimpleExoPlayer? = null

    var videoUrl = MutableLiveData<String?>()

    var isLoadingVideoPlayer = MutableLiveData(false)

    var isLive = false

    var timeCode: Long = 0

    var productList: List<Product>? = null

    var productTimeCodes: List<ProductTimeCodes>? = null

    fun logOnClickProduct(product: Product) {
        tracker.logFeaturedProductClicked(product)
    }
}
package com.pbj.sdk.videoPlayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.SimpleExoPlayer
import com.pbj.sdk.domain.product.model.Product

class VideoViewModel : ViewModel() {

    var videoPlayer: SimpleExoPlayer? = null

    var videoUrl = MutableLiveData<String?>()

    var isLoadingVideoPlayer = MutableLiveData(false)

    var isLive = false

    var productList: List<Product>? = null
}
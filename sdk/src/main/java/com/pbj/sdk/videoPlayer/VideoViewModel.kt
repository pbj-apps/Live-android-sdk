package com.pbj.sdk.videoPlayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.SimpleExoPlayer

class VideoViewModel : ViewModel() {

    var videoPlayer: SimpleExoPlayer? = null

    var videoUrl = MutableLiveData<String?>()

    var isLoadingVideoPlayer = MutableLiveData(false)

    var isLive = false
}
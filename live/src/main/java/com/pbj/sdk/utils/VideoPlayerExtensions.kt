package com.pbj.sdk.utils


import android.content.Context
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.net.URL

internal fun SimpleExoPlayer.setMediaSource(videoUrl: String, context: Context) {

    val url = URL(videoUrl)

    val mediaSource = if (url.path.endsWith(".m3u8")) {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory()

        val createMediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
            MediaItem.fromUri(videoUrl)
        )
        createMediaSource
    } else {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.packageName)
        )

        ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoUrl))
    }

    setMediaSource(mediaSource)
    prepare()
}
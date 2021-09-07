package com.pbj.sdk.common.ui

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.PlayerView.SHOW_BUFFERING_NEVER
import com.google.android.exoplayer2.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING
import com.pbj.sdk.common.ui.PlayerSettings.ResizeMode
import com.pbj.sdk.common.ui.PlayerSettings.ScalingMode
import com.pbj.sdk.utils.initMediaSource
import java.util.*
import kotlin.concurrent.schedule

@Composable
fun VideoPlayerView(
    url: String,
    timeCode: Long?,
    modifier: Modifier = Modifier,
    settings: PlayerSettings,
    soundEnabled: Boolean,
    onVideoPlayerStateChange: (VideoState) -> Unit
) {
    val context = LocalContext.current

    val playerInitTime = remember {
        Date().time
    }

    val mustPreparePlayer = remember {
        mutableStateOf(false)
    }

    val exoPlayer = remember(settings) {
        SimpleExoPlayer.Builder(context)
            .build()
            .apply {
                initMediaSource(url, context)
                onVideoPlayerStateChange.invoke(VideoState.LOADING)
                addListener(
                    object : Player.Listener {
                        override fun onPlaybackStateChanged(state: Int) {
                            super.onPlaybackStateChanged(state)

                            val videoState: VideoState = when (state) {
                                Player.STATE_READY -> VideoState.READY
                                Player.STATE_ENDED -> VideoState.ENDED
                                else -> VideoState.IDLE
                            }
                            onVideoPlayerStateChange.invoke(videoState)
                        }

                        override fun onPlayerError(error: ExoPlaybackException) {
                            super.onPlayerError(error)
                            onVideoPlayerStateChange.invoke(VideoState.LOADING)

                            Timer(false).schedule(delay = 5000) {
                                mustPreparePlayer.value = true
                            }
                        }
                    }
                )

                playWhenReady = settings.playWhenReady
                videoScalingMode = when (settings.scalingMode) {
                    ScalingMode.Fit -> C.VIDEO_SCALING_MODE_SCALE_TO_FIT
                    ScalingMode.FitWithCropping -> C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                }
            }
    }

    val currentVolume: Float = remember {
        exoPlayer.volume
    }

    if (soundEnabled)
        exoPlayer.volume = currentVolume
    else
        exoPlayer.volume = 0f

    timeCode?.let {
        val currentTime = Date().time
        val timeSinceInit = currentTime - playerInitTime
        val seekTime = timeSinceInit + it
        exoPlayer.seekTo(seekTime)
    }

    if (mustPreparePlayer.value) {
        exoPlayer.prepare()
        mustPreparePlayer.value = false
    }

    val resizeSetting = when (settings.resizeMode) {
        ResizeMode.Fit -> AspectRatioFrameLayout.RESIZE_MODE_FIT
        ResizeMode.FixedHeight -> AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
        ResizeMode.FixedWidth -> AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
        ResizeMode.Fill -> AspectRatioFrameLayout.RESIZE_MODE_FILL
        ResizeMode.Zoom -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
    }

    DisposableEffect(
        AndroidView(
            modifier = modifier,
            factory = {
                PlayerView(context).apply {
                    hideController()
                    useController = false
                    setShowBuffering(
                        if (settings.showBufferingWhenPlaying) SHOW_BUFFERING_WHEN_PLAYING
                        else SHOW_BUFFERING_NEVER
                    )
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                }
            }, update = {
                it.apply {
                    resizeMode = resizeSetting
                }
            })
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}

enum class VideoState {
    IDLE,
    LOADING,
    READY,
    ENDED
}

data class PlayerSettings(
    val resizeMode: ResizeMode = ResizeMode.Fit,
    val scalingMode: ScalingMode = ScalingMode.Fit,
    val showUI: Boolean = false,
    val playWhenReady: Boolean = true,
    val showBufferingWhenPlaying: Boolean = true
) {

    enum class ScalingMode {
        Fit,
        FitWithCropping
    }

    enum class ResizeMode {
        Fit,
        FixedWidth,
        FixedHeight,
        Fill,
        Zoom
    }
}
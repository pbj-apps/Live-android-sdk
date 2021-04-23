package com.pbj.sdk.videoPlayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.pbj.sdk.R
import com.pbj.sdk.utils.setMediaSource

class VideoPlayerFragment : Fragment() {

    interface LiveFragmentListener {
        fun onLiveFinished()
        fun onLiveReady()
        fun onPlayerError(errorMessage: String?)
    }

    var liveFragmentListener: LiveFragmentListener? = null

    val viewModel: VideoViewModel by viewModels()

    lateinit var playerView: PlayerView

    lateinit var backButton: AppCompatImageView

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (activity is LiveFragmentListener) {
            liveFragmentListener = activity as LiveFragmentListener
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let { _ ->
            if (savedInstanceState == null) {

                arguments?.apply {

                    viewModel.videoUrl.value = getString(VIDEO_URL)
                    viewModel.isLive = getBoolean(IS_LIVE)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_video_player, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.apply {
            playerView = findViewById(R.id.playerView)
            backButton = findViewById(R.id.backButton)
        }

        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        if (savedInstanceState == null) {
            context?.let {
                initPlayer(it)
                initVideoView(playerView)
            }
        }

        updateUiVisibility(false)
    }

    private fun initPlayer(context: Context) {
        viewModel.videoPlayer = SimpleExoPlayer.Builder(requireContext()).build().apply {
            addListener(
                object : Player.EventListener {
                    override fun onPlaybackStateChanged(state: Int) {
                        super.onPlaybackStateChanged(state)
                        viewModel.isLoadingVideoPlayer.value = state == Player.STATE_BUFFERING

                        if (state == Player.STATE_ENDED)
                            liveFragmentListener?.onLiveFinished()
                        if (state == Player.STATE_READY)
                            liveFragmentListener?.onLiveReady()
                    }

                    override fun onPlayerError(error: ExoPlaybackException) {
                        super.onPlayerError(error)
                        liveFragmentListener?.onPlayerError(error.localizedMessage)
                    }
                }
            )
            playWhenReady = true

            if (viewModel.isLive) {
                playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                videoScalingMode = Renderer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            }
        }

        viewModel.videoUrl.value?.let {

            viewModel.videoPlayer?.setMediaSource(it, context)
        }
    }

    private fun initVideoView(playerView: PlayerView) {
        playerView.apply {
            setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
            useController = !viewModel.isLive
            player = viewModel.videoPlayer
            setControllerVisibilityListener { visibility -> updateUiVisibility(visibility == View.VISIBLE) }
        }

    }

    private fun updateUiVisibility(isVisible: Boolean) {
        backButton.isVisible = isVisible
    }

    override fun onPause() {
        super.onPause()
        viewModel.videoPlayer?.stop()
    }

    companion object {
        @JvmStatic
        fun newInstance(video: String, isLive: Boolean = false) =
            VideoPlayerFragment().apply {
                arguments = Bundle().also {
                    it.also {
                        it.putString(VIDEO_URL, video)
                        it.putBoolean(IS_LIVE, isLive)
                    }
                }
            }

        private const val VIDEO_URL = "VIDEO_URL"
        private const val IS_LIVE = "IS_LIVE"
    }
}
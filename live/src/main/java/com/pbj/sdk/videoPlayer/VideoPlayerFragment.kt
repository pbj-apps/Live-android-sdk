package com.pbj.sdk.videoPlayer

import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pbj.sdk.databinding.FragmentVideoPlayerBinding
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.product.ProductAdapter
import com.pbj.sdk.utils.setMediaSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideoPlayerFragment : Fragment(), ProductAdapter.OnProductClickListener {

    interface LiveFragmentListener {
        fun onLiveFinished()
        fun onLiveReady()
        fun onPlayerError(errorMessage: String?)
    }

    lateinit var viewBinding: FragmentVideoPlayerBinding

    var liveFragmentListener: LiveFragmentListener? = null

    val vm: VideoViewModel by viewModels()

    private var productAdapter = ProductAdapter(this)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (activity is LiveFragmentListener) {
            liveFragmentListener = activity as? LiveFragmentListener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentVideoPlayerBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.apply {

            context?.let { _ ->
                if (savedInstanceState == null) {

                    arguments?.apply {
                        vm.videoUrl.value = getString(VIDEO_URL)
                        vm.isLive = getBoolean(IS_LIVE)
                        val products: Array<Product>? =
                            getParcelableArray(PRODUCT_LIST) as Array<Product>?
                        products?.toList()?.let {
                            vm.productList = it
                        }
                    }
                }
            }

            if (!vm.productList.isNullOrEmpty()) {
                initProductList()
            }

            if (savedInstanceState == null) {
                context?.let {
                    initPlayer(it)
                    initVideoView(playerView)
                }
            }

            backButton.setOnClickListener {
                activity?.onBackPressed()
            }

            updateUiVisibility(false)
        }
    }

    private fun initPlayer(context: Context) {
        vm.videoPlayer = SimpleExoPlayer.Builder(requireContext()).build().apply {
            addListener(
                object : Player.EventListener {
                    override fun onPlaybackStateChanged(state: Int) {
                        super.onPlaybackStateChanged(state)
                        vm.isLoadingVideoPlayer.value = state == Player.STATE_BUFFERING

                        if (state == Player.STATE_ENDED)
                            liveFragmentListener?.onLiveFinished()
                        if (state == Player.STATE_READY)
                            liveFragmentListener?.onLiveReady()
                    }

                    override fun onPlayerError(error: ExoPlaybackException) {
                        super.onPlayerError(error)

                        lifecycleScope.launch {
                            vm.isLoadingVideoPlayer.value = true
                            delay(5000)
                            vm.videoPlayer?.prepare()
                        }

                        liveFragmentListener?.onPlayerError(error.localizedMessage)
                    }
                }
            )
            playWhenReady = true

            viewBinding.apply {
                if (vm.isLive) {
                    playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    videoScalingMode = Renderer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                }
            }
        }

        vm.videoUrl.value?.let {
            vm.videoPlayer?.setMediaSource(it, context)
        }
    }

    private fun initVideoView(playerView: PlayerView) {
        playerView.apply {
            setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
            useController = !vm.isLive
            player = vm.videoPlayer
            setControllerVisibilityListener { visibility -> updateUiVisibility(visibility == View.VISIBLE) }
        }
    }

    private fun updateUiVisibility(isVisible: Boolean) {
        viewBinding.apply {
            backButton.isVisible = isVisible
        }
    }

    override fun onClickProduct(product: Product) {
        val params = PictureInPictureParams.Builder().build()
        activity?.enterPictureInPictureMode(params)

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(product.link)).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(browserIntent)
    }

    private fun initProductList() {
        viewBinding.apply {
            productListView.apply {
                adapter = productAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

//        productCount.text = productList.toString()
//
//        productButton.setOnClickListener {
//            toggleProductListVisibility()
//        }
        }
    }

    override fun onStop() {
        super.onStop()
        vm.videoPlayer?.stop()
    }

    companion object {
        @JvmStatic
        fun newInstance(
            video: String,
            isLive: Boolean = false,
            productList: List<Product>? = null
        ) =
            VideoPlayerFragment().apply {
                arguments = Bundle().also {
                    it.also {
                        it.putString(VIDEO_URL, video)
                        it.putBoolean(IS_LIVE, isLive)
                        it.putParcelableArray(PRODUCT_LIST, productList?.toTypedArray())
                    }
                }
            }

        private const val VIDEO_URL = "VIDEO_URL"
        private const val IS_LIVE = "IS_LIVE"
        private const val PRODUCT_LIST = "PRODUCT_LIST"
    }
}
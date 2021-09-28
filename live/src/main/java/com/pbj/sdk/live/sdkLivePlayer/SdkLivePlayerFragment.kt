package com.pbj.sdk.live.sdkLivePlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pbj.sdk.R
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.live.livePlayer.LivePlayerViewModel
import com.pbj.sdk.live.livePlayer.ui.LivePlayerScreen

class SdkLivePlayerFragment : Fragment() {

    interface LiveFragmentListener {
        fun onClickCard(product: Product)
        fun onBack()
    }

    private val sdkVm: SdkLivePlayerViewModel by viewModels()

    private val liveVm: LivePlayerViewModel by viewModels()

    private var listener: LiveFragmentListener? = null
    var showId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is LiveFragmentListener) {
            listener = activity as LiveFragmentListener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        // Dispose the Composition when the view's LifecycleOwner is destroyed
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            LiveView()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            showId = getString(SHOW_ID)
        }

        sdkVm.init(showId)
    }

    @Composable
    fun LiveView() {
        Crossfade(sdkVm.screenState) {
            when (it) {
                is SdkLivePlayerViewModel.State.Loading -> SdkLoadingEpisodeView()
                is SdkLivePlayerViewModel.State.NoLive -> SdkInformationView(
                    title = R.string.no_live,
                    description = R.string.no_live_description,
                    close = ::finish
                )
                is SdkLivePlayerViewModel.State.HasEpisode -> EpisodeView(it.episode)
                is SdkLivePlayerViewModel.State.HasShow -> SdkShowDetailsView(it.show, ::finish)
                is SdkLivePlayerViewModel.State.EpisodeEnd -> SdkEpisodeEndView(
                    show = it.episode?.show,
                    close = ::finish
                )
                is SdkLivePlayerViewModel.State.Error -> SdkInformationView(
                    title = R.string.livestream_error_title,
                    description = R.string.livestream_error_description,
                    close = ::finish
                )
            }
        }
    }

    @Composable
    private fun EpisodeView(episode: Episode) {
        LaunchedEffect(episode) {
            liveVm.init(episode, null)
        }

        LivePlayerScreen(
            vm = liveVm,
            onPlayerError = { onPlayerError(it.localizedMessage) },
            onPlayerStateChange = sdkVm::onPlayerStateChange,
            isChatEnabled = true,
            onClickBack = ::finish,
            onClickProduct = ::onClickProduct,
            onClickJoin = {}
        )
    }

    private fun onPlayerError(errorMessage: String?) {
        sdkVm.onError(errorMessage)
    }

    private fun onClickProduct(product: Product) {
        liveVm.logOnClickProduct(product)

    }

    private fun finish() {
        listener?.onBack()
    }

    companion object {
        private const val SHOW_ID = "SHOW_ID"

        fun newInstance(showId: String? = null) =
            SdkLivePlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(SHOW_ID, showId)
                }
            }
    }
}
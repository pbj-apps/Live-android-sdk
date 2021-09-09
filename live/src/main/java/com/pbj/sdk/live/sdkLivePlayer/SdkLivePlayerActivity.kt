package com.pbj.sdk.live.sdkLivePlayer

import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import com.pbj.sdk.R
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.live.livePlayer.LiveViewModel
import com.pbj.sdk.live.livePlayer.ui.LivePlayerScreen

class SdkLivePlayerActivity : AppCompatActivity() {

    private val sdkVm: SdkLivePlayerViewModel by viewModels()

    private val liveVm: LiveViewModel by viewModels()

    var showId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableScreenRotation(true)

        setContent {
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

        intent.extras?.apply {
            showId = getString(SHOW_ID)
        }

        sdkVm.init(showId)
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

    private fun enableScreenRotation(enable: Boolean) {
        requestedOrientation = if (enable)
            ActivityInfo.SCREEN_ORIENTATION_SENSOR
        else
            ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
    }

    private fun onPlayerError(errorMessage: String?) {
        sdkVm.onError(errorMessage)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun onClickProduct(product: Product) {
        liveVm.logOnClickProduct(product)
        val params = PictureInPictureParams.Builder().build()
        enterPictureInPictureMode(params)

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(product.link)).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(browserIntent)
    }

    companion object {
        private const val SHOW_ID = "SHOW_ID"

        fun startLivePlayer(context: Context, showId: String? = null) {
            startLivePlayerActivity(context, Bundle().apply {
                putString(SHOW_ID, showId)
            })
        }

        fun startLivePlayer(context: Context) {
            startLivePlayerActivity(context)
        }

        private fun startLivePlayerActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, SdkLivePlayerActivity::class.java).apply {
                bundle?.let {
                    putExtras(it)
                }
            }

            ContextCompat.startActivity(context, intent, bundle)
        }
    }
}
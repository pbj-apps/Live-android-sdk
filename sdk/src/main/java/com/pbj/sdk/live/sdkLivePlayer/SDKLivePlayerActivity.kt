package com.pbj.sdk.live.sdkLivePlayer

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pbj.sdk.databinding.ActivitySdkLivePlayerBinding
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.domain.live.model.Show
import com.pbj.sdk.domain.vod.model.previewImage
import com.pbj.sdk.live.livePlayer.LivePlayerFragment
import com.pbj.sdk.utils.observe
import com.pbj.sdk.utils.startFragment
import com.pbj.sdk.videoPlayer.VideoPlayerFragment

class SDKLivePlayerActivity : AppCompatActivity(), LivePlayerFragment.Listener,
    VideoPlayerFragment.LiveFragmentListener {

    lateinit var view: ActivitySdkLivePlayerBinding

    private val vm: SDKLivePlayerViewModel by viewModels()

    var showId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivitySdkLivePlayerBinding.inflate(layoutInflater)
        setContentView(view.root)

        intent.extras?.apply {
            showId = getString(SHOW_ID)
        }

        observerScreenState()
        vm.init(showId)
    }

    private fun observerScreenState() {
        observe(vm.screenState) { state ->
            when (state) {
                is SDKLivePlayerViewModel.State.Loading -> onLoading()
                is SDKLivePlayerViewModel.State.NoLive -> onNoLive()
                is SDKLivePlayerViewModel.State.HasEpisode -> onEpisode(state.episode)
                is SDKLivePlayerViewModel.State.HasShow -> onShow(state.show)
                is SDKLivePlayerViewModel.State.EpisodeEnd -> onEpisodeEnd(state.episode)
                is SDKLivePlayerViewModel.State.Error -> onError()
            }
        }
    }

    private fun onLoading() {
        replaceView(
            LoadingEpisodeView(this@SDKLivePlayerActivity)
        )
    }

    private fun onNoLive() {
        replaceView(
            NoEpisodeView(this).apply {
                closeButton.setOnClickListener {
                    finish()
                }
            }
        )
    }

    private fun onError() {
        replaceView(
            EpisodeErrorView(this).apply {
                closeButton.setOnClickListener {
                    finish()
                }
            }
        )
    }

    private fun onEpisode(episode: Episode) {
        val livePlayerFragment = LivePlayerFragment.newInstance(episode, null)
        startFragment(livePlayerFragment, view.content.id, false)
    }

    private fun onShow(show: Show) {
        replaceView(
            ShowPreview(this, show.previewAsset?.previewImage, show.title, show.description).apply {
                closeButton.setOnClickListener {
                    finish()
                }
            }
        )
    }

    private fun onEpisodeEnd(episode: Episode?) {
        replaceView(
            EpisodeEndView(this, episode?.show?.previewAsset?.previewImage).apply {
                closeButton.setOnClickListener {
                    finish()
                }
            }
        )
    }

    private fun replaceView(v: View) {
        view.content.apply {
            removeAllViews()
            addView(v)
        }
    }

    override fun onPressClose() {
        finish()
    }

    override fun enableScreenRotation(enable: Boolean) {
        requestedOrientation = if (enable)
            ActivityInfo.SCREEN_ORIENTATION_SENSOR
        else
            ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
    }

    override fun onPlayerError(errorMessage: String?) {
        vm.onError(errorMessage)
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

    override fun onLiveFinished() {
        vm.onLiveFinished()
    }

    override fun onLiveReady() {
        vm.onLiveReady()
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
            val intent = Intent(context, SDKLivePlayerActivity::class.java).apply {
                bundle?.let {
                    putExtras(it)
                }
            }

            ContextCompat.startActivity(context, intent, bundle)
        }
    }
}
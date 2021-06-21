package com.pbj.sdk.live.livePlayer

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.pbj.sdk.analytics.AnalyticsTracker
import com.pbj.sdk.databinding.ActivityLiveRoomBinding
import com.pbj.sdk.domain.live.model.Episode
import com.pbj.sdk.utils.startFragment
import org.koin.android.ext.android.inject
import timber.log.Timber

class LiveRoomActivity : AppCompatActivity(), LivePlayerFragment.Listener {

    private val analytics : AnalyticsTracker by inject()

    lateinit var view: ActivityLiveRoomBinding

    var episode : Episode? = null

    override fun onStart() {
        super.onStart()
        enableScreenRotation(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivityLiveRoomBinding.inflate(layoutInflater)
        setContentView(view.root)

        episode = null

        var nextEpisode: Episode? = null

        intent.extras?.apply {
            episode = getParcelable(LIVE_STREAM)
            nextEpisode = getParcelable(NEXT_LIVE_STREAM)
        }

        episode?.let {
            analytics.logLiveClassStarts(it)
        }

        val fragment = LivePlayerFragment.newInstance(
            episode = episode,
            nextEpisode = nextEpisode
        )

        startFragment(fragment, view.content.id)
    }

    override fun onPressClose() {
        onBackPressed()
    }

    override fun enableScreenRotation(enable: Boolean) {
        requestedOrientation = if (enable)
            ActivityInfo.SCREEN_ORIENTATION_SENSOR
        else
            ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
    }

    override fun onPlayerError(errorMessage: String?) {
        Timber.e(errorMessage)
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

    override fun onBackPressed() {
        episode?.let {
            analytics.logLiveClassExit(it)
        }
        finish()
    }

    companion object {
        const val LIVE_STREAM = "LIVE_STREAM"
        const val NEXT_LIVE_STREAM = "NEXT_LIVE_STREAM"
    }
}
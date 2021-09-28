package com.pbj.sdk.live.sdkLivePlayer

import android.annotation.SuppressLint
import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.live.livePlayer.LivePlayerViewModel

class SdkLivePlayerActivity : AppCompatActivity(), SdkLivePlayerFragment.LiveFragmentListener {

    private val sdkVm: SdkLivePlayerViewModel by viewModels()

    private val liveVm: LivePlayerViewModel by viewModels()

    var showId: String? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.apply {
            showId = getString(SHOW_ID)
        }

        sdkVm.init(showId)

        setContent {
            AndroidView({
                val fragment = SdkLivePlayerFragment.newInstance(showId)

                FragmentContainerView(it).apply {
                    id = 927672954
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                    supportFragmentManager.beginTransaction()
                        .replace(id, fragment)
                        .commit()
                }
            })
        }
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

    override fun onClickCard(product: Product) {
        liveVm.logOnClickProduct(product)
        val params = PictureInPictureParams.Builder().build()
        enterPictureInPictureMode(params)

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(product.link)).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(browserIntent)
    }

    override fun onBack() {
        finish()
    }

    companion object {
        private const val SHOW_ID = "SHOW_ID"

        fun getLivePlayerActivityIntent(context: Context, showId: String? = null) =
            getLivePlayerActivityIntent(context, Bundle().apply {
                putString(SHOW_ID, showId)
            })

        fun getLivePlayerActivityIntent(context: Context) =
            getLivePlayerActivityIntent(context, bundle = null)

        private fun getLivePlayerActivityIntent(context: Context, bundle: Bundle? = null) =
            Intent(context, SdkLivePlayerActivity::class.java).apply {
                bundle?.let {
                    putExtras(it)
                }
            }
    }
}
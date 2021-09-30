package com.pbj.studio.livesampleapp

import android.app.PictureInPictureParams
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.pbj.sdk.core.ApiEnvironment
import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.live.sdkLivePlayer.SdkLivePlayerFragment

class MainActivity : AppCompatActivity(), SdkLivePlayerFragment.LiveFragmentListener {

    private var sdkEnvironment: ApiEnvironment = ApiEnvironment.DEMO

    private lateinit var homeView: ConstraintLayout
    private lateinit var fragContainer: FragmentContainerView
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeView = findViewById(R.id.home)
        fragContainer = findViewById(R.id.fragContainer)

        val organizationApiKeyField: TextInputEditText = findViewById(R.id.organizationApiKeyField)
        val showIdField: TextInputEditText = findViewById(R.id.showIdField)

        val environmentPicker: RadioGroup = findViewById(R.id.environmentGroup)
        environmentPicker.check(R.id.demo)

        environmentPicker.setOnCheckedChangeListener { group, checkedId ->
            sdkEnvironment = when (checkedId) {
                R.id.dev -> ApiEnvironment.DEV
                R.id.demo -> ApiEnvironment.DEMO
                R.id.prod -> ApiEnvironment.PROD
                else -> ApiEnvironment.DEMO
            }
        }

        val watchButton: MaterialButton = findViewById(R.id.watchButton)
        watchButton.setOnClickListener {
            val apiKey = organizationApiKeyField.text.toString()
            val showId = showIdField.text.toString()
            startLiveActivity(apiKey, showId)
        }

        findViewById<MaterialButton>(R.id.watchFragment).setOnClickListener {
            val apiKey = organizationApiKeyField.text.toString()
            val showId = showIdField.text.toString()
            startLiveFragment(apiKey, showId)
        }
    }

    private fun startLiveActivity(apiKey: String, showId: String) {
        if (apiKey.isNotBlank()) {
            Live.init(this, apiKey, sdkEnvironment)
            val intent =
                Live.instance.getLivePlayerActivityIntent(this, showId)

            startActivity(intent)
        }
    }

    private fun startLiveFragment(apiKey: String, showId: String) {
        fragment = SdkLivePlayerFragment.newInstance(showId)
        if (apiKey.isNotBlank()) {
            Live.init(this, apiKey, sdkEnvironment)
            fragContainer.apply {
                isVisible = true
                supportFragmentManager.beginTransaction()
                    .replace(id, fragment)
                    .commit()
            }

            homeView.isVisible = false
        }
    }

    override fun onClickCard(product: Product) {
        val params = PictureInPictureParams.Builder().build()
        enterPictureInPictureMode(params)

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(product.link)).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(browserIntent)
    }

    override fun onBack() {
        homeView.isVisible = true
        fragContainer.apply {
            isVisible = false
            supportFragmentManager
                .beginTransaction()
                .remove(fragment)
                .commit()
        }
    }
}
package com.pbj.studio.livesampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.pbj.sdk.LiveSDK
import com.pbj.sdk.core.ApiEnvironment

class MainActivity : AppCompatActivity() {

    private var sdkEnvironment: ApiEnvironment = ApiEnvironment.DEMO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val organizationApiKeyField: TextInputEditText = findViewById(R.id.organizationApiKeyField)
        val showIdField: TextInputEditText = findViewById(R.id.showIdField)

        val environmentPicker: RadioGroup = findViewById(R.id.environmentGroup)
        environmentPicker.check(R.id.demo)

        environmentPicker.setOnCheckedChangeListener { group, checkedId ->
            sdkEnvironment = when(checkedId) {
                R.id.dev -> ApiEnvironment.DEV
                R.id.demo -> ApiEnvironment.DEMO
                R.id.prod -> ApiEnvironment.PROD
                else -> ApiEnvironment.DEMO
            }
        }

        val watchButton: MaterialButton = findViewById(R.id.watchButton)
        watchButton.setOnClickListener {
            val apiKey = organizationApiKeyField.text.toString()
            if (apiKey.isNotBlank()) {
                Live.init(this, apiKey, sdkEnvironment)
                Live.instance.startLivePlayer(this, showIdField.text.toString())
                Live.instance.isEpisodeLive { isLive ->

                }
            }
        }
    }
}
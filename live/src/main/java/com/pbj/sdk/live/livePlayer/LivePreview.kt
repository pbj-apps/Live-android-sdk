package com.pbj.sdk.live.livePlayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.pbj.sdk.databinding.LivePreviewLayoutBinding

class LivePreview @JvmOverloads constructor(
    context: Context,
    private val showTitle: String?,
    private val showDescription: String?,
    private val countdownValue: String?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var view: LivePreviewLayoutBinding =
        LivePreviewLayoutBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        view.apply {
            title.text = showTitle
            description.text = showDescription
            updateCountdown(countdownValue)
        }
    }

    fun updateCountdown(countdownValue: String?) {
        view.apply {
            countdown.text = countdownValue
        }
    }
}
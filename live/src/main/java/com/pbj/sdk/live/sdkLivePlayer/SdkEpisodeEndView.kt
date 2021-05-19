package com.pbj.sdk.live.sdkLivePlayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.pbj.sdk.databinding.SdkEpisodeEndLayoutBinding

class SdkEpisodeEndView @JvmOverloads constructor(
    context: Context,
    private val showPreview: String?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var view: SdkEpisodeEndLayoutBinding = SdkEpisodeEndLayoutBinding
        .inflate(LayoutInflater.from(context), this)

    val closeButton: AppCompatImageView = view.closeIcon

    init {
        view.apply {
            bgImage.load(showPreview)
        }
    }
}
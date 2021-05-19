package com.pbj.sdk.live.sdkLivePlayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.pbj.sdk.databinding.SdkNoLivestreamLayoutBinding

class SdkNoEpisodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val view: SdkNoLivestreamLayoutBinding =
        SdkNoLivestreamLayoutBinding.inflate(LayoutInflater.from(context), this)

    val closeButton: AppCompatImageView = view.noLivecloseIcon
}
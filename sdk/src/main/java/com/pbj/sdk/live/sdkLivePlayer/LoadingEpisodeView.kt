package com.pbj.sdk.live.sdkLivePlayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.pbj.sdk.databinding.LoadingLivestreamLayoutBinding

class LoadingEpisodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LoadingLivestreamLayoutBinding.inflate(LayoutInflater.from(context), this)
    }
}
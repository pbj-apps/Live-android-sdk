package com.pbj.sdk.live.sdkLivePlayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.pbj.sdk.databinding.ShowPreviewLayoutBinding

class ShowPreview @JvmOverloads constructor(
    context: Context,
    private val showPreview: String?,
    private val showTitle: String?,
    private val showDescription: String?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var view: ShowPreviewLayoutBinding = ShowPreviewLayoutBinding
        .inflate(LayoutInflater.from(context), this)

    val closeButton: AppCompatImageView = view.closeIcon

    init {
        view.apply {
            bgImage.load(showPreview)
            title.text = showTitle
            description.text = showDescription
        }
    }
}
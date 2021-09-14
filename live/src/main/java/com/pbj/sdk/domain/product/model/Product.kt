package com.pbj.sdk.domain.product.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val title: String? = null,
    val price: String? = null,
    val detail: String? = null,
    val image: String? = null,
    val link: String? = null,
    val highlightTimingList: List<ProductHighlightTiming>? = null
) : Parcelable
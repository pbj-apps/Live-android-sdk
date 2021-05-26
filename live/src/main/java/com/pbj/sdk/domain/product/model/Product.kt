package com.pbj.sdk.domain.product.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val title: String?,
    val price: String?,
    val detail: String?,
    val image: String?,
    val link: String?,
) : Parcelable
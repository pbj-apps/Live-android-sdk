package com.pbj.sdk.concreteImplementation.product.model

import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.domain.product.model.ProductHighlightTiming
import com.pbj.sdk.domain.product.model.ProductUpdate
import com.pbj.sdk.utils.DateUtils

internal val JsonProduct.asModel: Product
    get() = Product(
        id,
        title,
        price,
        description,
        image?.src,
        store_url,
        highlight_timings?.map { it.asModel }
    )

internal val JsonProductUpdate.asModel: ProductUpdate
    get() = ProductUpdate(
        highlighted_featured_products.mapNotNull { it.product?.asModel }
    )

internal val JsonProductHighlightTiming.asModel: ProductHighlightTiming
    get() {
        val startTime = DateUtils.getLocalTime(start_time)
        val endTime = DateUtils.getLocalTime(end_time)
        return ProductHighlightTiming(startTime, endTime)
    }
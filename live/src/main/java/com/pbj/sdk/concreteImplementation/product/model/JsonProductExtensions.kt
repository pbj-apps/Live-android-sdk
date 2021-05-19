package com.pbj.sdk.concreteImplementation.product.model

import com.pbj.sdk.domain.product.model.Product
import com.pbj.sdk.domain.product.model.ProductUpdate

internal val JsonProduct.asModel: Product
    get() = Product(
        id,
        title,
        price,
        description,
        image?.src,
        store_url
    )

internal val JsonProductUpdate.asModel: ProductUpdate
    get() = ProductUpdate(
        productList.map { it.asModel }
    )
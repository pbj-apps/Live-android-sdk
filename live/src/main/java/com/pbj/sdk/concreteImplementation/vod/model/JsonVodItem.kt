package com.pbj.sdk.concreteImplementation.vod.model

internal interface JsonVodItem {
    val id: String
    val title: String
    val description: String?
    val asset_type: String?
    val item_type: String?
}
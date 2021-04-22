package com.pbj.sdk.parser

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.net.URL

internal class UrlParser {
    @ToJson
    fun toJson(url: URL?): String? {
        return url?.toString()
    }

    @FromJson
    fun fromJson(url: String?): URL? = if (url == null) URL(url) else null
}
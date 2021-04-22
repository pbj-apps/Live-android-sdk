package com.pbj.sdk.parser

import com.pbj.sdk.utils.DateUtils
import com.pbj.sdk.utils.asJsonString
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.LocalDateTime

internal class DateParser {
    @ToJson
    fun toJson(date: LocalDate?): String? {
        return date?.asJsonString
    }

    @FromJson
    fun fromJson(dateString: String?): LocalDate? =
        DateUtils.getDate(dateString)
}

internal class DateTimeParser {
    @ToJson
    fun toJson(date: LocalDateTime?): String? {
        return date?.asJsonString
    }

    @FromJson
    fun fromJson(dateString: String?): LocalDateTime? =
        DateUtils.getDateTime(dateString)?.toLocalDateTime()
}
package com.pbj.sdk.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAccessor

object DateUtils {

    const val DEFAULT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val DAY_AT_TIME = "EEEE 'at' HH:mma"
    const val TIME = "HH:mm"
    const val LOCAL_TIME = "HH:mm:ss.SSSSSS"

    const val DAY_SHORT_NAME = "EE"
    const val DAY_FULL_NAME = "EEEE"

    fun getDate(dateString: String?, formatString: String? = null): LocalDate? {
        val formatter: DateTimeFormatter = if (formatString == null) {
            DateTimeFormatter.ISO_OFFSET_DATE_TIME
        } else {
            DateTimeFormatter.ofPattern(formatString)
        }
        return if (dateString != null)
            LocalDate.parse(dateString, formatter)
        else
            null
    }

    fun getDateTime(dateString: String?, formatString: String? = null): OffsetDateTime? {
        val formatter: DateTimeFormatter = if (formatString == null) {
            DateTimeFormatter.ISO_OFFSET_DATE_TIME
        } else {
            DateTimeFormatter.ofPattern(formatString)
        }
        return if (dateString != null)
            OffsetDateTime.parse(dateString, formatter)
        else
            null
    }

    fun getLocalTime(dateString: String?, formatString: String? = null): LocalTime? {
        val formatter: DateTimeFormatter = if (formatString == null) {
            DateTimeFormatter.ofPattern(LOCAL_TIME)
        } else {
            DateTimeFormatter.ofPattern(formatString)
        }
        return if (dateString != null)
            LocalTime.parse(dateString, formatter)
        else
            null
    }
}

fun OffsetDateTime.isSameDayThan(testDate: OffsetDateTime?): Boolean {
    val dateDay = truncatedTo(ChronoUnit.DAYS)
    return dateDay.equals(testDate?.truncatedTo(ChronoUnit.DAYS))
}

fun LocalDateTime.isSameDayThan(testDate: LocalDateTime?): Boolean {
    val dateDay = truncatedTo(ChronoUnit.DAYS)
    return dateDay.equals(testDate?.truncatedTo(ChronoUnit.DAYS))
}

fun TemporalAccessor.toString(format: String?): String =
    DateTimeFormatter.ofPattern(format).format(this)

val TemporalAccessor.asJsonString: String
    get() = toString(DateUtils.DEFAULT)

val OffsetDateTime.asDayAtTime: String
    get() = toString(DateUtils.DAY_AT_TIME)

val OffsetDateTime.asTime: String
    get() = toString(DateUtils.TIME)

val TemporalAccessor.dayShortName: String
    get() = toString(DateUtils.DAY_SHORT_NAME)

val TemporalAccessor.dayFullName: String
    get() = toString(DateUtils.DAY_FULL_NAME)

val LocalTime?.asMilliSeconds: Long
    get() = this?.toSecondOfDay()?.times(1000)?.toLong() ?: 0

package com.global.loveto.core.extension

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val FULL_DATE = "yyyy-MM-dd HH:mm:ss Z"
const val HUMAN_DATE = "MM/dd HH:mm"
const val SHORT_DATE = "yyyy/MM/dd"
const val FULL_TIME = "HH:mm:ss"
const val TIME = "HH:mm"
const val AMERICAN_FULL_FORMAT = "MM/dd/yy, hh:mm aa"
const val AMERICAN_TIME_FORMAT = "hh:mm aa"
const val AMERICAN_DATE_FORMAT = "MM/dd/yy"

fun String.toDate(format: String = FULL_DATE): Date {
    return try {
        if (this.isNotEmpty()) {
            val format = SimpleDateFormat(format)
            format.parse(this)
        } else {
            Date()
        }
    } catch (ex: Exception) {
        Date()
    }
}

fun Date.toFirebase(format: String = FULL_DATE): String {
    return this.milisecondsToDate(format)
}

fun Long.milisecondsToDays(): Int {
    return (((((this) / 1000) / 60) / 60) / 24).toInt()
}

fun Date.milisecondsToDate(format: String = SHORT_DATE): String {
    return this.time.milisecondsToDate(format)
}

fun Long.isToday(): Boolean {
    val date = Calendar.getInstance()
    date.time = Date(this)
    val currentDate = Calendar.getInstance()
    return (date.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)) && (date.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH)) && (date.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR))
}

fun Date.milisecondsToDate(format: DateFormat): String {
    val pattern = (format as SimpleDateFormat).toLocalizedPattern()
    return this.milisecondsToDate(pattern)
}

fun Long.milisecondsToDate(format: String = SHORT_DATE): String {
    val format = SimpleDateFormat(format)
    return format.format(this)
}

fun Long.milisecondsToHour(format: String = FULL_TIME): String {
    val format = SimpleDateFormat(format)
    return format.format(this)
}

fun Date.toSimpleDate(): Date {
    return this.time.milisecondsToDate().toDate(SHORT_DATE)
}

fun Date.toReadableDate(format: String = SHORT_DATE): String {
    return this.time.milisecondsToDate(format)
}

fun getCurrentDateString(pattern: String = "yyyyMMdd_HHmmss"): String {
    return SimpleDateFormat(pattern).format(Date())
}

fun getCurrentHourString(pattern : String = "HH") : String {
    return SimpleDateFormat(pattern).format(Date())
}

fun getCurrentDayString(pattern : String = "dd") : String {
    return SimpleDateFormat(pattern).format(Date())
}

fun getCurrentMonthString(pattern : String = "MM") : String {
    return SimpleDateFormat(pattern).format(Date())
}



fun Int.secondsToLegibleTime(): String {
    val hours = this / 3600
    val secondsLeft = this - hours * 3600
    val minutes = secondsLeft / 60
    val seconds = secondsLeft - minutes * 60

    var formattedTime = ""
    if (hours < 10)
        formattedTime += "0"
    formattedTime += "$hours:"

    if (minutes < 10)
        formattedTime += "0"
    formattedTime += "$minutes:"

    if (seconds < 10)
        formattedTime += "0"
    formattedTime += seconds


    return formattedTime
}

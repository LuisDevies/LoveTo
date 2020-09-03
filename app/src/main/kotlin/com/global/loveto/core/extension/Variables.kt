package com.global.loveto.core.extension

import java.text.NumberFormat
import java.util.*

fun Any.castToDouble(): Double {
    return when (this) {
        is Double -> this
        is Long -> this.toDouble()
        is String -> {
            var value: Double = 0.0
            try {
                value = this.toDouble()
            } catch (ex: Exception) {
            }
            return value
        }
        is Int -> this.toDouble()
        is Boolean -> {
            return if (this) 1.0
            else 0.0
        }
        else -> 0.0
    }
}

fun Any.castToLong(): Long {
    return when (this) {
        is Long -> this
        is Double -> this.toLong()
        is String -> {
            var value: Long = 0
            try {
                value = this.toLong()
            } catch (ex: Exception) {
            }
            return value
        }
        is Int -> this.toLong()
        is Boolean -> {
            return if (this) 1
            else 0
        }
        else -> 0
    }
}

fun Any.castToInt(): Int {
    return when (this) {
        is Int -> this
        is Long -> this.toInt()
        is Double -> this.toInt()
        is String -> {
            var value = 0
            try {
                value = this.toInt()
            } catch (ex: Exception) {
            }
            return value
        }
        is Boolean -> {
            return if (this) 1
            else 0
        }
        else -> 0
    }
}

fun Any.castToBoolean(): Boolean {
    return when (this) {
        is Boolean -> this
        is Int -> this > 0
        is Long -> this > 0
        is Double -> this > 0
        is String -> {
            return when ((this.trim())?.toLowerCaseCustom()) {
                "true" -> true
                "ok" -> true
                "1" -> true
                else -> false
            }
        }
        else -> false
    }
}

fun Any.castToString(): String {
    return when (this) {
        is String -> this.trim()
        is Boolean -> "true"
        is Int -> this.toString()
        is Long -> this.toString()
        is Double -> {
            String.format("%.2f", this)
        }
        else -> this.toString()
    }
}

fun String?.toLowerCaseCustom(): String {
    return try {
        this?.toLowerCase() ?: ""
    } catch (exc: java.lang.Exception) {
        ""
    }
}

fun String.getLocaleFromCurrency(): Locale? {
    NumberFormat.getAvailableLocales().forEach {
        if (NumberFormat.getCurrencyInstance(it).currency.currencyCode == this) return it
    }
    return null
}
package com.global.loveto.core.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import java.io.InputStream
import java.util.*

const val log_tag = "instantgoTAG"

val Context.networkInfo: NetworkInfo?
    get() =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

fun logd(msg: Any?, tag: String = "") {
    val finalMsg: String = msg?.castToString() ?: "LOG ERROR"
    Log.d("$log_tag$tag", finalMsg)
}

fun logi(msg: Any?, tag: String = "") {
    val finalMsg: String = msg?.castToString() ?: "LOG ERROR"
    Log.i("$log_tag$tag", finalMsg)
}

fun logw(msg: Any?, tag: String = "") {
    val finalMsg: String = msg?.castToString() ?: "LOG ERROR"
    Log.w("$log_tag$tag", finalMsg)
}

fun loge(msg: Any?, tag: String = "") {
    val finalMsg: String = msg?.castToString() ?: "LOG ERROR"
    Log.e("$log_tag$tag", finalMsg)
}

fun Context.readAmazonCredentials(): String? {
    var json: String? = null
    try {
        val inputStream: InputStream = this.assets.open("amazon-credentials.json")
        json = inputStream.bufferedReader().use { it.readText() }
    } catch (ex: Exception) {
        ex.printStackTrace()
        return null
    }
    return json
}

private fun geocodeLocation(context: Context, location: Location): MutableList<Address> {
    val geocoder = Geocoder(context, Locale.getDefault())
    return geocoder.getFromLocation(location.latitude, location.longitude, 1)
}

fun Context.getCityFromLocation(location: Location?): String? {
    return if (location != null) {
        val addresses =
            geocodeLocation(this, location)
        val city = when {
            addresses.get(0)?.locality?.isNotEmpty() == true -> {
                addresses.get(0)?.locality
            }
            addresses.get(0)?.subAdminArea?.isNotEmpty() == true -> {
                addresses.get(0)?.subAdminArea
            }
            addresses.get(0)?.subLocality?.isNotEmpty() == true -> {
                addresses.get(0)?.subLocality
            }
            else -> {
                ""
            }
        }
        city
    } else {
        ""
    }
}

fun Context.getStateFromLocation(location: Location?): String? {
    return if (location != null) {
        val addresses =
            geocodeLocation(this, location)
        addresses[0].subAdminArea
    } else {
        ""
    }
}

fun Context.getCountryFromLocation(location: Location?): String? {
    return if (location != null) {
        val addresses =
            geocodeLocation(this, location)
        addresses[0].countryCode
    } else {
        ""
    }
}

fun Context.startActivityNoBackStack(intent: Intent) {
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    this.startActivity(intent)
}

fun Context.startActivityBackStack(intent: Intent) {
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    this.startActivity(intent)
}

fun Context.startActivityBackStackForResult(intent: Intent, tag: Int) {
    if (this is Activity) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        this.startActivityForResult(intent, tag)
    }
}

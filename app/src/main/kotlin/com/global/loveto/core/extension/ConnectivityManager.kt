package com.global.loveto.core.extension

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build


fun ConnectivityManager.isNetworkAvailable() = activeNetworkInfo?.isConnected == true

fun ConnectivityManager.isWifiAvailable() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    getNetworkCapabilities(activeNetwork).hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
} else {
    val mWifi: NetworkInfo = getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    mWifi.isConnected
}


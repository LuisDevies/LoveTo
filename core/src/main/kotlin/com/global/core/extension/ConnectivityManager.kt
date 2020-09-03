package com.up.core.extension

import android.net.ConnectivityManager

fun ConnectivityManager.isNetworkAvailable() = activeNetworkInfo?.isConnected == true

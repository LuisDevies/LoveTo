package com.global.loveto.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject

class RouteActivity : AppCompatActivity() {

    var action = ""

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        goToAction(intent.extras?.getString("action") ?: "")
        super.onResume()
    }

    private fun goToAction(action: String) {
        when (action) {
            "" -> {
                navigator.showMain(this)
            }
            else -> {
                /*logd(action)*/
            }
        }
    }

}
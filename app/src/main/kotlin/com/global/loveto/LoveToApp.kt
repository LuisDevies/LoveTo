package com.global.loveto

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.global.loveto.di.appModule
import com.global.loveto.preferences.SharedPreferencesHelper
import org.koin.android.ext.android.getKoin

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named

const val URL_BASE_LOVE_TO_API = BuildConfig.URL_BASE_LOVE_TO_API


val DATABASE_NAME : String
    get() {
        return "love_to.sqlite-${SharedPreferencesHelper.userId}.sqlite"
    }
const val DATABASE_VERSION = 1
const val TABLE_USER = "user"
const val TABLE_FARMER = "farmer"
const val TABLE_OPERATION = "operation"


open class LoveToApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@LoveToApp)
            modules(listOf(appModule))
            getKoin().createScope("session", named("session"))
        }
    }

    fun refreshSessionScope() {
        getKoin().getScopeOrNull("session")?.close()
        getKoin().createScope("session", named("session"))
    }
}

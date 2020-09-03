package com.global.loveto.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.global.loveto.core.extension.empty
import com.global.loveto.preferences.Values.PREFS_USER_ID
import org.koin.core.KoinComponent
import org.koin.core.inject



object SharedPreferencesHelper : KoinComponent {

    private val preferences: SharedPreferences by inject()

    const val SHARED_PREFERENCES_NAME = "loveTo"

    fun getCurrentPreferences() = preferences

    var userId: String?
        get() = preferences.getString(Values.PREFS_USER_ID, "")
        set(value) {
            preferences.edit {
                putString(Values.PREFS_USER_ID, value).apply()
            }
        }

    var userName: String?
        get() = preferences.getString(Values.PREFS_USER_NAME, String.empty())
        set(value) = preferences.edit().putString(Values.PREFS_USER_NAME, value).apply()

    fun cleanSession() {
        preferences.edit().remove(Values.PREFS_USER_ID).apply()
        preferences.edit().remove(Values.PREFS_USER_NAME).apply()
    }

}
package com.example.myapplication

import android.content.SharedPreferences
import android.content.Context
import java.io.Serializable

class PreferencesManager(context: Context): Serializable {
    private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    init {
        preferences = context.getSharedPreferences(
            PREFERENCE_NAME,
            PRIVATE_MODE)
        editor = preferences.edit()
    }
    fun isFirstRun() = preferences.getBoolean(FIRST_TIME, true)
    fun setFirstRun() {
        editor.putBoolean(FIRST_TIME, false).commit()
        editor.commit()
    }
    companion object {
        private const val PRIVATE_MODE = 0
        private const val PREFERENCE_NAME = "configuration"
        private const val FIRST_TIME = "isFirstRun"
    }
}
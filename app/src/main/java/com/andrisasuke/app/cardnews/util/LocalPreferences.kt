package com.andrisasuke.app.cardnews.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.andrisasuke.app.cardnews.BuildConfig

class LocalPreferences(val context: Context) {

    companion object {
        val NEWS_CACHE = "news_cache"
    }

    val prefs: SharedPreferences by lazy { context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE) }

    @Suppress("UNCHECKED_CAST")
    private fun <T> findPreference(name: String, default: T?): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("Type is unknown")
        }
        res as T
    }

    @SuppressLint("CommitPrefEdits")
    private fun <T> putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }.apply()
    }
}

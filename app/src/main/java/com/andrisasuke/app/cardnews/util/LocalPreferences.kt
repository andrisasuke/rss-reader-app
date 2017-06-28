package com.andrisasuke.app.cardnews.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.andrisasuke.app.cardnews.BuildConfig
import com.andrisasuke.app.cardnews.model.DataSource
import com.andrisasuke.app.cardnews.model.NewsHolder
import com.google.gson.Gson

class LocalPreferences(val context: Context) {

    companion object {
        val NEWS_CACHE = "news_cache"
        val NEWS_SOURCE = "news_source";
    }

    val prefs: SharedPreferences by lazy { context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE) }

    val gson by lazy { Gson() }

    fun storeNewsSource(source: String) {
        putPreference(NEWS_SOURCE, source)
    }

    fun getNewsSource(): String = findPreference(NEWS_SOURCE, DataSource.values[0])

    fun storeNewsCache(newsHolder: NewsHolder){
        val json = gson.toJson(newsHolder)
        putPreference(NEWS_CACHE, json)
    }

    fun getNewsCache(): NewsHolder? {
        val json: String = findPreference(NEWS_CACHE, "")
        if(json != "") return gson.fromJson(json, NewsHolder::class.java)
        else return null
    }

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

package com.converter.core

import android.app.Application
import android.content.SharedPreferences
import com.converter.core.Constants.PREFERENCES_FILE_NAME
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object PreferencesManager {

    lateinit var sp: SharedPreferences

    fun with(application: Application) {
        sp = application.getSharedPreferences(PREFERENCES_FILE_NAME, Application.MODE_PRIVATE)
    }

    fun <T> put(`object`: T, key: String) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        sp.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        val value = sp.getString(key, null)
        return GsonBuilder().create().fromJson(value, object : TypeToken<T>() {}.type)
    }
}
package com.currency.converter.base

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

interface Repo {
    suspend fun isInternetUnavailable(): Boolean
}

class NetworkRepository (private val application: Application)   {

    fun isInternetUnavailable() : Boolean {
        val manager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork == null
    }
}
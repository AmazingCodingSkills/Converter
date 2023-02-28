package com.currency.converter.base.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager


class NetworkRepositoryImpl(private val application: Application) :
    NetworkRepository {

    override suspend fun isInternetUnavailable(): Boolean {
        val manager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork == null
    }
}
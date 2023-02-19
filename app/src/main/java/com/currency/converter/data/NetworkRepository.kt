package com.currency.converter.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.currency.converter.domain.NetworkInterface


class NetworkRepository(private val application: Application) : NetworkInterface {

    override suspend fun isInternetUnavailable(): Boolean {
        val manager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork == null
    }
}
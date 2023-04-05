package com.converter.core.presentation.networkfragment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject


class NetworkRepositoryImpl @Inject constructor(private val application: Application) :
    NetworkRepository {

    override suspend fun isInternetAvailable(): Boolean {
        val manager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork != null
    }
}
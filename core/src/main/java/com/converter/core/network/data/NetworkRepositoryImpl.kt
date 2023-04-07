package com.converter.core.network.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.converter.core.network.domain.NetworkRepository
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
package com.currency.converter.base.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject


class NetworkRepositoryImpl @Inject constructor(private val application: Application) :
    NetworkRepository {
    // если я тут указываю Inject
    // то это мне позволяет не делать provide в модуле
    // по факту при создании мы говорим, что нужно инжектать этот конструктор
    // чтобы инджектать сюда зависимости
    // и все зависимости которые есть в конструкторе будут разрешены в момент создания объекта

    override suspend fun isInternetAvailable(): Boolean {
        val manager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork != null
    }
}
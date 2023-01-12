package com.currency.converter.base

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Rest API - стандарт передачи данных между клиентом и сервером
// Методы - get, put, post, head, delete, patch
// get -> идем на сервер и тупо забируем данные / достать список стран
// post -> вызвать сервер для обновления данных / залогиниться
// head -> очень похож на гет, но без скачки данных
// put -> для обновления данных на сервере
// body, header  - протокол http

object RetrofitProvider {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.currencyscoop.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                ).build()
            )
            .build()
    }
    val api: CurrencyService by lazy {
        retrofit.create(CurrencyService::class.java)
    }
}
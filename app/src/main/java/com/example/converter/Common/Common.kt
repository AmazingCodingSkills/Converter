package com.example.converter.Common

import com.example.converter.Interface.RetrofitServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Common {
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("https://api.currencyscoop.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            //.client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build())
            .build()
    }
    val api: RetrofitServices by lazy {
        retrofit.create(RetrofitServices::class.java)
    }
    /*private val BASE_URL = "https://api.currencyscoop.com/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)*/
}
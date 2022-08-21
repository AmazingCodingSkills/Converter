package com.example.converter.Interface

import com.example.converter.adapters.Information
import com.example.converter.adapters.TestResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


    interface RetrofitServices {
        @GET("latest?api_key=10c6aafaa5395ef3ffa5d47d973cfd26")
        fun getInformationList(): Call<TestResponse>
    }


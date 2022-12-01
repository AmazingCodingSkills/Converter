package com.currency.converter.base

import com.currency.converter.features.favorite.MetaCurrenciesResponse
import com.currency.converter.features.rate.RatesMetaResponse
import retrofit2.Call
import retrofit2.http.GET


interface CurrencyService {
    @GET("latest?api_key=10c6aafaa5395ef3ffa5d47d973cfd26")
    fun getInformationList(): Call<RatesMetaResponse>
    @GET("currencies?api_key=10c6aafaa5395ef3ffa5d47d973cfd26")
    fun getInformationListX(): Call<MetaCurrenciesResponse>
}



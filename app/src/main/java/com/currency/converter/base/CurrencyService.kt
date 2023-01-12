package com.currency.converter.base

import com.currency.converter.features.favorite.MetaCurrenciesResponse
import com.currency.converter.features.rate.RatesMetaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyService {
    @GET("latest?api_key=10c6aafaa5395ef3ffa5d47d973cfd26")
    fun getLatestValueCurrency(
        @Query("symbols") symbols: List<String>? = null,
        @Query("base") base: String = "USD"
    ): Call<RatesMetaResponse>

    @GET("currencies?api_key=10c6aafaa5395ef3ffa5d47d973cfd26")
    fun getNameCountryCurrency(): Call<MetaCurrenciesResponse>
}



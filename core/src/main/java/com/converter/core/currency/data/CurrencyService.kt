package com.converter.core.currency.data

import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyService {
    @GET("latest")
    suspend fun getRates(
        @Query("symbols") symbols: List<String>? = null,
        @Query("base") base: String = "USD"
    ): RatesMetaResponse

    @GET("currencies")
    suspend fun getNameCountryCurrency(): MetaCurrenciesResponse
}



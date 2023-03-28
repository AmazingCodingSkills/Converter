package com.converter.core.currency

import com.converter.core.favoritemodel.MetaCurrenciesResponse
import com.converter.core.ratemodel.RatesMetaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyService {
    @GET("latest?api_key=10c6aafaa5395ef3ffa5d47d973cfd26")
    suspend fun getRates(
        @Query("symbols") symbols: List<String>? = null,
        @Query("base") base: String = "USD"
    ): RatesMetaResponse

    @GET("currencies?api_key=10c6aafaa5395ef3ffa5d47d973cfd26")
    fun getNameCountryCurrency(): Call<MetaCurrenciesResponse>
}



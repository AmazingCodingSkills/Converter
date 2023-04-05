package com.converter.core.data

import com.converter.core.data.currencymodel.MetaCurrenciesResponse
import com.converter.core.data.ratemodel.RatesMetaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyService {
    @GET("latest")
    suspend fun getRates(
        @Query("symbols") symbols: List<String>? = null,
        @Query("base") base: String = "USD"
    ): RatesMetaResponse

    @GET("currencies")
     fun getNameCountryCurrency(): Call<MetaCurrenciesResponse>
}



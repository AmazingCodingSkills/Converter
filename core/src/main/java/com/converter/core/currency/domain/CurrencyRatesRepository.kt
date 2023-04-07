package com.converter.core.currency.domain


interface CurrencyRatesRepository {

    suspend fun getLatestApiResult(base: String): List<RateItem>

    suspend fun loadAllValueCurrency()

}
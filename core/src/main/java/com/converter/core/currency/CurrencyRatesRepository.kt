package com.converter.core.currency

interface CurrencyRatesRepository {

    suspend fun getLatestApiResult(base: String): List<RateItem>

}
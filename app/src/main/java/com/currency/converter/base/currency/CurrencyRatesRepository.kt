package com.currency.converter.base.currency

import com.currency.converter.features.rate.domain.RateItem

interface CurrencyRatesRepository {
    suspend fun getLatestApiResultCoroutine(base: String): List<RateItem>
}
package com.currency.converter.base.currency

import com.currency.converter.features.rate.domain.RateItem

interface CurrencyRatesRepository {

    suspend fun getLatestApiResult(base: String): List<RateItem>

}
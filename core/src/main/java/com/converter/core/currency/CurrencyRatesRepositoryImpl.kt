package com.converter.core.currency

import javax.inject.Inject


class CurrencyRatesRepositoryImpl @Inject constructor(private val currencyService: CurrencyService) :
    CurrencyRatesRepository {

    override suspend fun getLatestApiResult(base: String): List<RateItem> {
        val response = currencyService.getRates(base = base).ratesResponse
        return RateItemMapper.map(response)
    }
}
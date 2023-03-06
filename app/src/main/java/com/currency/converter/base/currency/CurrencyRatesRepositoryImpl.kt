package com.currency.converter.base.currency

import com.currency.converter.base.mappers.RateItemMapper
import com.currency.converter.features.rate.domain.RateItem
import javax.inject.Inject


class CurrencyRatesRepositoryImpl @Inject constructor(private val currencyService:CurrencyService) : CurrencyRatesRepository {

    override suspend fun getLatestApiResult(base: String): List<RateItem> {
        val response = currencyService.getRates(base = base).ratesResponse
        return RateItemMapper.map(response)
    }
}
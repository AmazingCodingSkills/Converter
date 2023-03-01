package com.currency.converter.base.currency

import com.currency.converter.base.mappers.RateItemMapper
import com.currency.converter.base.retrofit.RetrofitProvider
import com.currency.converter.features.rate.domain.RateItem


class CurrencyRatesRepositoryImpl() : CurrencyRatesRepository {

    override suspend fun getLatestApiResult(base: String): List<RateItem> {
        val response = RetrofitProvider.api.getRates(base = base).ratesResponse
        return RateItemMapper.map(response)
    }
}
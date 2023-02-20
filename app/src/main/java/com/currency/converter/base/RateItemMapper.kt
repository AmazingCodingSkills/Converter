package com.currency.converter.base

import com.currency.converter.features.rate.domain.Currency
import com.currency.converter.features.rate.domain.RateItem

class RateItemMapper() {
    suspend fun map(base: String) : List<RateItem> {
        val response = RetrofitProvider.api.getRates(base = base).ratesResponse
        return response.let {
            it.rates.map {
                RateItem(
                    date = response.date,
                    referenceCurrency = Currency(
                        name = it.key,
                        value = it.value
                    ),
                    baseCurrencyName = response.base
                )
            }
        }
    }
}
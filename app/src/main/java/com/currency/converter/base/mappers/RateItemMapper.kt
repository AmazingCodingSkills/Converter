package com.currency.converter.base.mappers

import com.currency.converter.base.ratemodel.RatesResponse
import com.currency.converter.features.rate.domain.Currency
import com.currency.converter.features.rate.domain.RateItem

object RateItemMapper {
     fun map(ratesResponse: RatesResponse) : List<RateItem> {
        return ratesResponse.let {response ->
            response.rates.map {
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
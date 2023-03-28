package com.converter.core.mappers

import com.converter.core.currency.Currency
import com.converter.core.currency.RateItem
import com.converter.core.ratemodel.RatesResponse

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
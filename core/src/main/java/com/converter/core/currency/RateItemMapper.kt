package com.converter.core.currency

import com.converter.core.currency.domain.Currency
import com.converter.core.currency.domain.RateItem
import com.converter.core.currency.data.RatesResponse

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
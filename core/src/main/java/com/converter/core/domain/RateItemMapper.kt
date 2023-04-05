package com.converter.core.domain

import com.converter.core.data.currencyrate.Currency
import com.converter.core.data.currencyrate.RateItem
import com.converter.core.data.ratemodel.RatesResponse

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
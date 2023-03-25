package com.converter.core.currency

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
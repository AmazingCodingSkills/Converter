package com.converter.core.currency

import com.converter.core.currency.domain.CurrencyItem
import com.converter.core.currency.data.ResponseCurrencies

object CurrencyItemMapper {
    fun map(responseCurrencies: ResponseCurrencies) : List<CurrencyItem> {
        return responseCurrencies.let {response ->
            response.fiats.map {
                CurrencyItem(
                    id = it.value.currency_code,
                    currencyName = it.value.currency_name,
                    isFavorite = false
                )
            }
        }
    }
}
package com.converter.core.domain

import com.converter.core.data.currencymodel.CurrencyItem
import com.converter.core.data.currencymodel.ResponseCurrencies

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
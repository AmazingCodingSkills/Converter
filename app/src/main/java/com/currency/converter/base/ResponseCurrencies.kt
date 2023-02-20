package com.currency.converter.base


data class ResponseCurrencies (
    val fiats: Map<String, CurrencyModel>
)

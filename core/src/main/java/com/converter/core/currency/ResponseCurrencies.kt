package com.converter.core.currency


data class ResponseCurrencies (
    val fiats: Map<String, CurrencyModel>
)

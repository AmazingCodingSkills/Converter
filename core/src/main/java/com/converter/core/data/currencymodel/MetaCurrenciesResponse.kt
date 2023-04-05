package com.converter.core.data.currencymodel

import com.converter.core.data.ratemodel.Meta


data class MetaCurrenciesResponse(
    val meta: Meta,
    val response: ResponseCurrencies
)
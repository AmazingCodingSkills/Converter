package com.currency.converter.base.favoritemodel

import com.currency.converter.base.ratemodel.Meta


data class MetaCurrenciesResponse(
    val meta: Meta,
    val response: ResponseCurrencies
)
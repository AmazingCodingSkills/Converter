package com.converter.core.favoritemodel

import com.converter.core.ratemodel.Meta


data class MetaCurrenciesResponse(
    val meta: Meta,
    val response: ResponseCurrencies
)
package com.currency.converter.features.rate

import java.util.*

data class RateItem(
    val date: Date,
    val referenceCurrency: Currency,
    val baseCurrencyName: String
)

data class Currency(
    val name: String,
    val value: Double
)

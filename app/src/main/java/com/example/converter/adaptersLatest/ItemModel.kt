package com.example.converter.adaptersLatest

import java.util.*

data class ItemModel(
    val date: Date,
    val referenceCurrency: ValueCurrency,
    val baseCurrencyName: String
)

data class ValueCurrency(
    val name: String,
    val value: Double
)

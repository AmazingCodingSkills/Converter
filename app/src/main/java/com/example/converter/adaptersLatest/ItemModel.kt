package com.example.converter.adaptersLatest

data class ItemModel(
    val date: String,
    val referenceCurrency: ValueCurrency,
    val baseCurrencyName: String
)

data class ValueCurrency(
    val name: String,
    val value: Double
)

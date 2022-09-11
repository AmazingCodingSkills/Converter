package com.example.converter.adaptersLatest

data class ItemModel(
    val date: String,
    val currency: ValueCurrency,
    val base: String
)
data class ValueCurrency(
    val currency: String,
    //val value: String
    )

package com.example.converter.adaptersLatest

import com.example.converter.adaptersCurrencies.Fiats

data class Response(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

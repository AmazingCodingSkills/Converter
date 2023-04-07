package com.converter.core.currency.data

import java.util.*

data class RatesResponse(
    val base: String,
    val date: Date,
    val rates: Map<String, Double>
)

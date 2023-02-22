package com.currency.converter.base.ratemodel

import java.util.*

data class RatesResponse(
    val base: String,
    val date: Date,
    val rates: Map<String, Double>
)
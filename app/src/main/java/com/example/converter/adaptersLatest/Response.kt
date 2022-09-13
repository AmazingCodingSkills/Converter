package com.example.converter.adaptersLatest

import java.util.*

data class Response(
    val base: String,
    val date: Date,
    val rates: Map<String, Double>
)

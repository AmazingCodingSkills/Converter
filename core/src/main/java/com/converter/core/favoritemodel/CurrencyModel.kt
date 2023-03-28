package com.converter.core.favoritemodel

import com.google.gson.annotations.SerializedName

data class CurrencyModel (
    @SerializedName("countries")
    val countries1: List<String>,
    val currency_code: String,
    val currency_name: String,
    val decimal_units: String
)


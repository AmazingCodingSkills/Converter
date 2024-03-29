package com.converter.core.currency.data

import com.google.gson.annotations.SerializedName

data class RatesMetaResponse(
    val meta: Meta,
    @SerializedName("response")
    val ratesResponse: RatesResponse
)
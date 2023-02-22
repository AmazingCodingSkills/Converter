package com.currency.converter.base.ratemodel

import com.google.gson.annotations.SerializedName

data class RatesMetaResponse(
    val meta: Meta,
    @SerializedName("response")
    val ratesResponse: RatesResponse
)
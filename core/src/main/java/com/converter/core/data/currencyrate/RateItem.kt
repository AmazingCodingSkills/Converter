package com.converter.core.data.currencyrate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class RateItem(
    val date: Date,
    val referenceCurrency: Currency,
    val baseCurrencyName: String
) : Parcelable

@Parcelize
data class Currency(
    val name: String,
    val value: Double
) : Parcelable


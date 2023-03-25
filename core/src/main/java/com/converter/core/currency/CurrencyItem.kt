package com.converter.core.currency

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// table 1  | table 2

// korzina (order) | tovar

// table - row1 (id, sum, date, foreight key id_tovar)   table 2 - row 1 (primary key id, name)

@Parcelize
data class CurrencyItem(
    val id: String,
    val currencyName: String,
    val isFavorite: Boolean,
) : Parcelable





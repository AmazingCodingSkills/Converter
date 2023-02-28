package com.currency.converter.base.favoritemodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyItem(
    val id: String,
    val currencyName: String,
    val isFavorite: Boolean
) : Parcelable



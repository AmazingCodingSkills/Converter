package com.currency.converter.base.favoritemodel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// table 1  | table 2

// korzina (order) | tovar

// table - row1 (id, sum, date, foreight key id_tovar)   table 2 - row 1 (primary key id, name)

@Parcelize
@Entity(tableName = "CurrencyItem")
data class CurrencyItem(
    val id: String,
    @PrimaryKey val currencyName: String,
    val isFavorite: Boolean,
) : Parcelable

/*@Parcelize
@Entity(tableName = "Favorite")
data class Favorite(
    val id: String,
    @PrimaryKey val currencyName: String,
    val isFavorite: Boolean,
    var isDisplayed: Boolean = true
) : Parcelable*/



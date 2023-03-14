package com.currency.converter.base.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
data class Favorite(
    @PrimaryKey val id: String,
    val currencyName: String,
    var isFavorite: Boolean,
)
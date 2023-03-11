package com.currency.converter.base.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Favorite")
data class Favorite(
    @PrimaryKey val id: String,
    val currencyName: String,
    var isFavorite: Boolean,
) : Parcelable
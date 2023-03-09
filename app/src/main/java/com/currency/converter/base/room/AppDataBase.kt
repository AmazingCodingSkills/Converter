package com.currency.converter.base.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.currency.converter.base.favoritemodel.CurrencyItem

@Database(
    entities = [CurrencyItem::class], version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyItemDao(): CurrencyItemDao
}






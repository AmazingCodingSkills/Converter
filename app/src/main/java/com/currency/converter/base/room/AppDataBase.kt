package com.currency.converter.base.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Favorite::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyItemDao(): CurrencyItemDao
}






package com.converter.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.converter.core.favorite.data.CurrencyItemDao
import com.converter.core.favorite.data.Favorite

@Database(
    entities = [Favorite::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyItemDao(): CurrencyItemDao
}






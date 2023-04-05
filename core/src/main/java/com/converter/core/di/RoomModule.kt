package com.converter.core.di

import androidx.room.Room
import com.converter.core.ConverterApplication
import com.converter.core.data.room.AppDatabase
import com.converter.core.data.room.CurrencyItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Provides
    @Singleton
    fun providesRoom(): CurrencyItemDao {
        val db = Room.databaseBuilder(
            ConverterApplication.application.applicationContext,
            AppDatabase::class.java, "Favorite"
        ).build()
        return db.currencyItemDao()
    }

}
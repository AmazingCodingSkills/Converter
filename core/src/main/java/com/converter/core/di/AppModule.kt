package com.converter.core.di

import android.app.Application
import com.converter.core.data.currencyrate.CurrencyRatesRepository
import com.converter.core.data.currencyrate.CurrencyRatesRepositoryImpl
import com.converter.core.data.CurrencyService
import com.converter.core.data.favouritecurrencymodel.FavouriteCurrencyRepository
import com.converter.core.data.favouritecurrencymodel.FavouriteCurrencyRepositoryImpl
import com.converter.core.presentation.networkfragment.NetworkRepository
import com.converter.core.presentation.networkfragment.NetworkRepositoryImpl
import com.converter.core.data.room.CurrencyItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RoomModule::class, RetrofitNetworkModule::class])
object AppModule {

    @Provides
    @Singleton
    fun providesNetworkRepositoryImpl(application: Application): NetworkRepository {
        return NetworkRepositoryImpl(application)
    }

    @Provides
    @Singleton
    fun provideCurrencyRatesRepository(currencyService: CurrencyService): CurrencyRatesRepository {
        return CurrencyRatesRepositoryImpl(currencyService)
    }

    @Provides
    @Singleton
    fun provideFavouriteCurrencyRepository(currencyItemDao: CurrencyItemDao): FavouriteCurrencyRepository {
        return FavouriteCurrencyRepositoryImpl(currencyItemDao)
    }

}
package com.converter.core.di

import android.app.Application
import com.converter.core.currency.domain.CurrencyRatesRepository
import com.converter.core.currency.data.CurrencyRatesRepositoryImpl
import com.converter.core.currency.data.CurrencyService
import com.converter.core.favorite.domain.FavouriteCurrencyRepository
import com.converter.core.favorite.data.FavouriteCurrencyRepositoryImpl
import com.converter.core.network.domain.NetworkRepository
import com.converter.core.network.data.NetworkRepositoryImpl
import com.converter.core.favorite.data.CurrencyItemDao
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
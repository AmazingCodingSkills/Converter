package com.converter.core.di

import android.app.Application
import com.converter.core.currency.domain.CurrencyRatesRepository
import com.converter.core.currency.data.CurrencyService
import com.converter.core.favorite.domain.FavouriteCurrencyRepository
import com.converter.core.network.domain.NetworkRepository
import com.converter.core.favorite.data.CurrencyItemDao
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun networkRepository(): NetworkRepository

    fun currencyRatesRepository(): CurrencyRatesRepository

    fun providesCurrencyService(): CurrencyService

    fun providesRoom(): CurrencyItemDao

    fun provideFavouriteCurrencyRepository(): FavouriteCurrencyRepository

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}

@Scope
annotation class FragmentScope



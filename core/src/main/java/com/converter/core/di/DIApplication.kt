package com.converter.core.di

import android.app.Application
import com.converter.core.data.currencyrate.CurrencyRatesRepository
import com.converter.core.data.CurrencyService
import com.converter.core.data.favouritecurrencymodel.FavouriteCurrencyRepository
import com.converter.core.presentation.networkfragment.NetworkRepository
import com.converter.core.data.room.CurrencyItemDao
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



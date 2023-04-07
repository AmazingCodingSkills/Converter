package com.example.rate.di

import com.converter.core.favorite.domain.FavouriteCurrencyRepository
import com.converter.core.di.AppComponent
import com.converter.core.di.FragmentScope
import com.example.rate.presentation.favourite.allcurrency.CurrenciesViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides


@Module
class CurrenciesModule {

    @Provides
    fun currenciesViewModelFactory(favouriteCurrencyRepository: FavouriteCurrencyRepository): CurrenciesViewModelFactory =
        CurrenciesViewModelFactory(favouriteCurrencyRepository)

}


@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [CurrenciesModule::class])
interface CurrenciesComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CurrenciesComponent
    }

    fun currenciesViewModelFactory(): CurrenciesViewModelFactory

    fun provideFavouriteCurrencyRepository(): FavouriteCurrencyRepository

}
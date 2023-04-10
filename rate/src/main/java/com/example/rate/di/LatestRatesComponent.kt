package com.example.rate.di

import com.converter.core.di.AppComponent
import com.converter.core.di.FragmentScope
import com.converter.core.favorite.domain.FavouriteCurrencyRepository
import com.converter.core.network.domain.NetworkRepository
import com.example.rate.presentation.latestrates.FactoryRatesViewModel
import com.example.rate.presentation.selectcurrency.SelectedCurrencyRepository
import com.example.rate.data.SelectedCurrencyRepositoryImpl
import com.example.rate.domain.UseCaseGetRates
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class LatestRatesModule {

    @Provides
    fun provideSelectedCurrencyRepositoryImpl(): SelectedCurrencyRepository {
        return SelectedCurrencyRepositoryImpl()
    }


    @Provides
    fun factoryRatesViewModel(
        networkRepository: NetworkRepository,
        selectedCurrencyRepository: SelectedCurrencyRepository,
        useCaseGetRates: UseCaseGetRates,
        favouriteCurrencyRepository: FavouriteCurrencyRepository
    ): FactoryRatesViewModel =
        FactoryRatesViewModel(networkRepository, selectedCurrencyRepository, useCaseGetRates,favouriteCurrencyRepository)
}

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [LatestRatesModule::class])
interface LatestRatesComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): LatestRatesComponent
    }

    fun factoryRatesViewModel(): FactoryRatesViewModel


}
package com.example.rate.di

import com.converter.core.AppComponent
import com.converter.core.FragmentScope
import com.converter.core.network.NetworkRepository
import com.example.rate.presentation.FactoryRatesViewModel
import com.example.rate.presentation.SelectedCurrencyRepository
import com.example.rate.presentation.SelectedCurrencyRepositoryImpl
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
        useCaseGetRates: UseCaseGetRates
    ): FactoryRatesViewModel =
        FactoryRatesViewModel(networkRepository, selectedCurrencyRepository, useCaseGetRates)
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
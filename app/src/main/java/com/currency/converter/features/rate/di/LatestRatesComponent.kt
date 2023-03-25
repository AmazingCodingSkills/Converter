package com.currency.converter.features.rate.di

import com.currency.converter.AppComponent
import com.currency.converter.FragmentScope
import com.currency.converter.base.SelectedCurrencyRepositoryImpl
import com.converter.core.network.NetworkRepository
import com.currency.converter.features.rate.domain.SelectedCurrencyRepository
import com.currency.converter.features.rate.domain.UseCaseGetRates
import com.currency.converter.features.rate.presentation.FactoryRatesViewModel
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
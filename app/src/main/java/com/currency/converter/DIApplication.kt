package com.currency.converter

import android.app.Application
import com.currency.converter.base.SelectedCurrencyRepositoryImpl
import com.currency.converter.base.currency.CurrencyRatesRepository
import com.currency.converter.base.currency.CurrencyRatesRepositoryImpl
import com.currency.converter.base.network.NetworkRepository
import com.currency.converter.base.network.NetworkRepositoryImpl
import com.currency.converter.features.calculator.domain.UseCaseGetCurrentRates
import com.currency.converter.features.calculator.presentation.CalculatorFragment
import com.currency.converter.features.calculator.presentation.FactoryCalculatorViewModel
import com.currency.converter.features.rate.data.FavouriteCurrencyRepositoryImpl
import com.currency.converter.features.rate.domain.FavouriteCurrencyRepository
import com.currency.converter.features.rate.domain.SelectedCurrencyRepository
import com.currency.converter.features.rate.domain.UseCaseGetRates
import com.currency.converter.features.rate.presentation.FactoryRatesViewModel
import com.currency.converter.features.rate.presentation.LatestRatesFragment
import dagger.*
import javax.inject.Scope
import javax.inject.Singleton


@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun latestRatesComponent(): LatestRatesComponent.Builder

    fun calculatorComponent(): CalculatorComponent.Builder
/*
    fun inject(fragment: LatestRatesFragment)

    fun inject(fragment: CalculatorFragment)*/ // пиздец, из-за этого я не мог унести все что мне нужно

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}

@Module(subcomponents = [LatestRatesComponent::class, CalculatorComponent::class])
object AppModule {

    @Provides
    fun providesNetworkRepositoryImpl(application: Application): NetworkRepository {
        return NetworkRepositoryImpl(application)
    }

    @Provides
    fun provideCurrencyRatesRepository(): CurrencyRatesRepository {
        return CurrencyRatesRepositoryImpl()
    }

}

@Scope
annotation class LatestRates

@Module
class LatestRatesModule() {

    @Provides
    fun provideSelectedCurrencyRepositoryImpl(): SelectedCurrencyRepository {
        return SelectedCurrencyRepositoryImpl()
    }

    @Provides
    fun provideFavouriteCurrencyRepository(): FavouriteCurrencyRepository {
        return FavouriteCurrencyRepositoryImpl()
    }

    @Provides
    fun factoryRatesViewModel(
        networkRepository: NetworkRepository,
        selectedCurrencyRepository: SelectedCurrencyRepository,
        useCaseGetRates: UseCaseGetRates
    ): FactoryRatesViewModel =
        FactoryRatesViewModel(networkRepository, selectedCurrencyRepository, useCaseGetRates)
}

@LatestRates
@Subcomponent(modules = [LatestRatesModule::class])
interface LatestRatesComponent : CalculatorComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): LatestRatesComponent
    }

    fun inject(fragment: LatestRatesFragment)
}

@Scope
annotation class Calculator

@Module
class CalculatorModule() {
    @Provides
    fun factoryCalculatorViewModel(
        networkRepository: NetworkRepository,
        useCaseGetCurrentRates: UseCaseGetCurrentRates
    ): FactoryCalculatorViewModel =
        FactoryCalculatorViewModel(networkRepository, useCaseGetCurrentRates)

}

@Calculator
@Subcomponent(modules = [CalculatorModule::class])
interface CalculatorComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): CalculatorComponent
    }

    fun inject(fragment: CalculatorFragment)
}


package com.currency.converter.features.calculator.di

import com.currency.converter.AppComponent
import com.currency.converter.FragmentScope
import com.converter.core.network.NetworkRepository
import com.currency.converter.features.calculator.domain.UseCaseGetCurrentRates
import com.currency.converter.features.calculator.presentation.FactoryCalculatorViewModel
import dagger.Component
import dagger.Module
import dagger.Provides


@Module
class CalculatorModule {

    @Provides
    fun factoryCalculatorViewModel(
        networkRepository: NetworkRepository,
        useCaseGetCurrentRates: UseCaseGetCurrentRates
    ): FactoryCalculatorViewModel =
        FactoryCalculatorViewModel(networkRepository, useCaseGetCurrentRates)
}

@FragmentScope
@Component(dependencies = [AppComponent::class],modules = [CalculatorModule::class])
interface CalculatorComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CalculatorComponent
    }

    fun factoryCalculatorViewModel(): FactoryCalculatorViewModel
}

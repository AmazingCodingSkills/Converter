package com.example.calculator.di


import com.converter.core.presentation.networkfragment.NetworkRepository
import com.converter.core.di.AppComponent
import com.converter.core.di.FragmentScope
import com.example.calculator.presentation.FactoryCalculatorViewModel
import com.example.calculator.domain.UseCaseGetCurrentRates
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

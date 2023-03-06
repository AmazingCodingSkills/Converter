package com.currency.converter.features.rate.di

import com.currency.converter.AppComponent
import com.currency.converter.FragmentScope
import com.currency.converter.features.rate.presentation.FactorySelectCurrencyViewModel
import dagger.Component
import dagger.Module


@Module
class SelectCurrencyModule() {

}

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [SelectCurrencyModule::class])
interface SelectCurrencyComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): SelectCurrencyComponent
    }

    fun factorySelectCurrencyViewModel(): FactorySelectCurrencyViewModel.Factory

}
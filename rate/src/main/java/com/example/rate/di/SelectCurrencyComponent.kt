package com.example.rate.di

import com.converter.core.AppComponent
import com.converter.core.FragmentScope
import com.example.rate.presentation.FactorySelectCurrencyViewModel
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
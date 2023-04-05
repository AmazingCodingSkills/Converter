package com.example.rate.di

import com.converter.core.di.AppComponent
import com.converter.core.di.FragmentScope
import com.example.rate.presentation.selectcurrency.FactorySelectCurrencyViewModel
import dagger.Component
import dagger.Module


@Module
class SelectCurrencyModule

@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [SelectCurrencyModule::class])
interface SelectCurrencyComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): SelectCurrencyComponent
    }

    fun factorySelectCurrencyViewModel(): FactorySelectCurrencyViewModel.Factory

}
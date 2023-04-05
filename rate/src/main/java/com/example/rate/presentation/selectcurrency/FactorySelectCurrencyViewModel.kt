package com.example.rate.presentation.selectcurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class FactorySelectCurrencyViewModel @AssistedInject constructor(
    @Assisted("referenceCurrency") private val referenceCurrency: String,
    @Assisted("baseCurrency") private val baseCurrency: String
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return SelectCurrencyViewModel(referenceCurrency, baseCurrency) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("referenceCurrency") referenceCurrency: String,
            @Assisted("baseCurrency") baseCurrency: String
        ): FactorySelectCurrencyViewModel
    }

}
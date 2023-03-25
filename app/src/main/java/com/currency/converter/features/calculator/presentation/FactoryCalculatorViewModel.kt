package com.currency.converter.features.calculator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.converter.core.network.NetworkRepository
import com.currency.converter.features.calculator.domain.UseCaseGetCurrentRates
import javax.inject.Inject

class FactoryCalculatorViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val useCaseGetCurrentRates: UseCaseGetCurrentRates
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return CalculatorViewModel(
            networkRepository,
            useCaseGetCurrentRates
        ) as T
    }
}
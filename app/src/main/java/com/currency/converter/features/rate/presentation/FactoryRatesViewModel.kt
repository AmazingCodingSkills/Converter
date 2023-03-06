package com.currency.converter.features.rate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.currency.converter.base.network.NetworkRepository
import com.currency.converter.features.rate.domain.SelectedCurrencyRepository
import com.currency.converter.features.rate.domain.UseCaseGetRates
import javax.inject.Inject


class FactoryRatesViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val selectedCurrencyRepository: SelectedCurrencyRepository,
    private val useCaseGetRates: UseCaseGetRates
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return RatesViewModel(
            networkRepository,
            selectedCurrencyRepository,
            useCaseGetRates
        ) as T
    }
}

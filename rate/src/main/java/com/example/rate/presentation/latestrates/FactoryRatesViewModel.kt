package com.example.rate.presentation.latestrates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.converter.core.favorite.domain.FavouriteCurrencyRepository
import com.converter.core.network.domain.NetworkRepository
import com.example.rate.domain.UseCaseGetRates
import com.example.rate.presentation.selectcurrency.SelectedCurrencyRepository
import javax.inject.Inject


class FactoryRatesViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val selectedCurrencyRepository: SelectedCurrencyRepository,
    private val useCaseGetRates: UseCaseGetRates,
    private val favouriteCurrencyRepository: FavouriteCurrencyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return RatesViewModel(
            networkRepository,
            selectedCurrencyRepository,
            useCaseGetRates,
            favouriteCurrencyRepository
        ) as T
    }
}

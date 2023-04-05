package com.example.rate.presentation.favourite.allcurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.converter.core.data.favouritecurrencymodel.FavouriteCurrencyRepository


import javax.inject.Inject

class CurrenciesViewModelFactory @Inject constructor(
    private val favouriteCurrencyRepository: FavouriteCurrencyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return CurrenciesViewModel(
            favouriteCurrencyRepository
        ) as T
    }
}
package com.example.rate.presentation.favourite.onlyfavourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.converter.core.favorite.domain.FavouriteCurrencyRepository

import javax.inject.Inject

class OnlyFavouritesViewModelFactory @Inject constructor(
    private val favouriteCurrencyRepository: FavouriteCurrencyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return OnlyFavoritesViewModel(
            favouriteCurrencyRepository
        ) as T
    }
}
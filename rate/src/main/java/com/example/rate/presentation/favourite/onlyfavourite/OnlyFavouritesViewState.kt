package com.example.rate.presentation.favourite.onlyfavourite

import com.converter.core.data.currencymodel.CurrencyItem
import com.example.rate.presentation.latestrates.RatesViewState

sealed interface OnlyFavouritesViewState {

    object Remove : OnlyFavouritesViewState

    object Empty : OnlyFavouritesViewState

    data class Content(val items: List<CurrencyItem>) : OnlyFavouritesViewState

}
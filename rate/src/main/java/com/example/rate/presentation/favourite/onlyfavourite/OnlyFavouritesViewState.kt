package com.example.rate.presentation.favourite.onlyfavourite

import com.converter.core.currency.domain.CurrencyItem

sealed interface OnlyFavouritesViewState {

    object Remove : OnlyFavouritesViewState

    object Empty : OnlyFavouritesViewState

    data class Content(val items: List<CurrencyItem>) : OnlyFavouritesViewState

}
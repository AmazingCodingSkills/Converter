package com.example.rate.presentation.favourite.allcurrency

import com.converter.core.data.currencymodel.CurrencyItem

sealed interface CurrenciesViewState {

    object Update : CurrenciesViewState

    data class Content(val items: List<CurrencyItem>) : CurrenciesViewState

}
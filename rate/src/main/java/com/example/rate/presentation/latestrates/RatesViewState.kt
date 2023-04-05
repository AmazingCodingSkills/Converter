package com.example.rate.presentation.latestrates


import com.converter.core.data.currencyrate.RateItem

sealed interface RatesViewState {

    object Loading : RatesViewState

    data class Content(val items: List<RateItem>, val icon: Int) : RatesViewState

    object Empty : RatesViewState

}
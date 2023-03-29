package com.example.rate.presentation


import com.converter.core.currency.RateItem

sealed interface RatesViewState {

    object Loading : RatesViewState

    data class Content(val items: List<RateItem>, val icon: Int) : RatesViewState

}
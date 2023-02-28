package com.currency.converter.features.rate.presentation


import com.currency.converter.features.rate.domain.RateItem

sealed interface RatesViewState {

    object Loading : RatesViewState

    data class Content(val items: List<RateItem>, val icon: Int) : RatesViewState

}
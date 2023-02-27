package com.currency.converter.features.rate.presentation


sealed interface RatesViewAction {
    object SelectCurrency : RatesViewAction
    data class UpdateCurrency(val base: String, val icon: Int) : RatesViewAction
}
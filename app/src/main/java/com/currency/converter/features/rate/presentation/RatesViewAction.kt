package com.currency.converter.features.rate.presentation


sealed interface RatesViewAction {
    object SelectCurrency : RatesViewAction
    object UpdateCurrency : RatesViewAction
}
package com.example.rate.presentation


sealed interface RatesViewAction {

    object SelectCurrency : RatesViewAction

    object UpdateCurrency : RatesViewAction

}
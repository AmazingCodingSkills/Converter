package com.example.rate.presentation.latestrates


sealed interface RatesViewAction {

    object SelectCurrency : RatesViewAction

    object UpdateCurrency : RatesViewAction

}
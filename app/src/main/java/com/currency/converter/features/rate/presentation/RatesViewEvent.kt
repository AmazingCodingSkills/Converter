package com.currency.converter.features.rate.presentation

sealed interface RatesViewEvent {

    object ShowErrorDialog: RatesViewEvent

}
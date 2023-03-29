package com.example.rate.presentation

sealed interface RatesViewEvent {

    object ShowErrorDialog: RatesViewEvent

}
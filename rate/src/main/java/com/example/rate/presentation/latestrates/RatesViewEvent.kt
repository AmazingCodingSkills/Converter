package com.example.rate.presentation.latestrates

sealed interface RatesViewEvent {

    object ShowErrorDialog: RatesViewEvent

}
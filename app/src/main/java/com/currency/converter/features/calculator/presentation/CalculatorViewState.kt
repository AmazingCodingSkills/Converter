package com.currency.converter.features.calculator.presentation

sealed interface CalculatorViewState {

    object Empty : CalculatorViewState

    data class Content(val resultFrom: Double?, val resultTo: Double?, val from: String, val to: String) : CalculatorViewState
}
package com.example.calculator.presentation

sealed interface CalculatorViewState {

    data class Empty(val defaultFrom: String, val defaultTo: String) : CalculatorViewState

    data class Content(val resultFrom: Double?, val resultTo: Double?, val from: String, val to: String) :
        CalculatorViewState
}
package com.example.calculator.presentation

sealed interface CalculatorViewAction {
    data class CurrencyConvertedOne(val input: String) : CalculatorViewAction
    data class CurrencyConvertedTwo(val input: String) : CalculatorViewAction
    data class CurrencySetFrom(val currencyCode: String,val input: String) : CalculatorViewAction
    data class CurrencySetTo(val currencyCode: String,val input: String) : CalculatorViewAction
    object InternetError: CalculatorViewAction
}
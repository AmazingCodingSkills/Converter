package com.currency.converter.features.calculator.presentation

sealed interface CalculatorViewEvent{

    object ShowErrorDialog: CalculatorViewEvent

}
package com.example.calculator.presentation

sealed interface CalculatorViewEvent{

    object ShowErrorDialog: CalculatorViewEvent

}
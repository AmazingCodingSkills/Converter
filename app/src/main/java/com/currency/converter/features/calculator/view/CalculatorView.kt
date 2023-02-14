package com.currency.converter.features.calculator.view

interface CalculatorView {
    fun setDefaultValueFrom (selectedCurrency: String)
    fun setDefaultValueTo (value: String)
    fun getResultOne(resultOne: Double)
    fun getResultTwo(resultTwo: Double)
    fun setCurrencies(to: String,from: String)
    fun clearFrom()
    fun clearTo()
}
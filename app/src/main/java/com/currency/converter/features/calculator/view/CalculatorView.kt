package com.currency.converter.features.calculator.view

interface CalculatorView {
    fun setDefaultValueFrom (selectedCurrency: String)
    fun setDefaultValueTo (value: String)
    fun setResultOneConversion(resultOne: Double)
    fun setResultTwoConversion(resultTwo: Double)
    fun showDialog()
    fun showToast(message: Int)
    fun setCurrencies(to: String,from: String)
    fun clearFrom()
    fun clearTo()
}
package com.currency.converter.features.calculator.presenter

import com.currency.converter.base.CurrencyRatesRepository
import com.currency.converter.features.calculator.view.CalculatorView

class CalculatorPresenter {

    private var view: CalculatorView? = null
    private var from = "USD"
    private var to = "EUR"

    fun attachView(view: CalculatorView) {
        this.view = view
    }


    private fun onCurrencyConverted(
        baseCurrencyCode: String,
        input: String,
        referenceCurrencyCode: String
    ) {
        val value = input.toDouble()
        CurrencyRatesRepository.getCurrentRates(
            baseCurrencyCode = baseCurrencyCode,
            referenceCurrencyCode = referenceCurrencyCode
        ) {
            val result = value * it
            view?.getResultOne(result)
        }
    }

    private fun onCurrencyConvertedTwo(
        baseCurrencyCode: String,
        input: String,
        referenceCurrencyCode: String
    ) {
        val value = input.toDouble()
        CurrencyRatesRepository.getCurrentRates(
            baseCurrencyCode = baseCurrencyCode,
            referenceCurrencyCode = referenceCurrencyCode
        ) {
            val result = value * it
            view?.getResultTwo(result)
        }
    }


    fun setFrom(selectedCurrency: String, input: String) {
        onCurrencyConverted(
            selectedCurrency,
            input,
            to
        )
        from = selectedCurrency
    }

    fun setTo(value: String, input: String) {
        onCurrencyConvertedTwo(
            value,
            input,
            to
        )
        to = value
    }

    fun onTextInputChangedOne(input: String) {
        if (input.isNotEmpty()) {
            onCurrencyConverted(from, input, to)
        } else {
            view?.clearFrom()
        }
    }

    fun onTextInputChangedTwo(input: String) {
        if (input.isNotEmpty()) {
            onCurrencyConvertedTwo(to, input, from)
        } else {
            view?.clearTo()
        }
    }

    fun onStarted() {
        view?.setCurrencies(to, from)
    }

    fun detachView() {
        this.view = null
    }

}
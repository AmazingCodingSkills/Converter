package com.currency.converter.features.calculator.presenter

import com.currency.converter.base.CurrencyRatesRepository
import com.currency.converter.base.NetworkRepository
import com.currency.converter.features.calculator.view.CalculatorView

class CalculatorPresenter(private val networkRepository: NetworkRepository) {

    private var view: CalculatorView? = null
    private var from = "USD"
    private var to = "EUR"

    fun attachView(view: CalculatorView) {
        this.view = view
    }

    fun setFrom(selectedCurrencyFromOne: String, input: String) {
        if (input.isNotEmpty()) {
            onCurrencyConverted(
                selectedCurrencyFromOne,
                input,
                to
            )
            from = selectedCurrencyFromOne
            view?.setDefaultValueFrom(from)
        }
    }

    fun setTo(selectedCurrencyFromTwo: String, input: String) {
        if (input.isNotEmpty()) {
            onCurrencyConverted(
                selectedCurrencyFromTwo,
                input,
                from
            )
            to = selectedCurrencyFromTwo
            view?.setDefaultValueTo(to)
        }
    }

    fun onTextInputChangedOne(input: String) {

        if (isInputValid(input)) {
            onCurrencyConverted(from, input, to)
        } else {
            view?.clearFrom()
            view?.clearTo()
        }
    }

    fun onTextInputChangedTwo(input: String) {
        if (isInputValid(input)) {
            onCurrencyConverted(to, input, from)
        } else {
            view?.clearFrom()
            view?.clearTo()
        }
    }

    private fun isInputValid(input: String): Boolean =
        input.isNotEmpty() && input != "."


    private fun onCurrencyConverted(
        baseCurrencyCode: String,
        input: String,
        referenceCurrencyCode: String
    ) {
        val value = input.toDouble()
        if (!networkRepository.isInternetUnavailable()) {
            CurrencyRatesRepository.getCurrentRates(
                baseCurrencyCode = baseCurrencyCode,
                referenceCurrencyCode = referenceCurrencyCode
            ) {
                val result = value * it
                if (from == baseCurrencyCode && to == referenceCurrencyCode) {
                    view?.setResultOneConversion(result)
                } else {
                    view?.setResultTwoConversion(result)
                }
            }

        } else {
            view?.showDialog()
        }
    }

    fun onStarted() {
        view?.setCurrencies(to, from)
    }

    fun onDialogWarning() {
        if (networkRepository.isInternetUnavailable()) {
            view?.showDialog()
        }
    }

    fun detachView() {
        this.view = null
    }

}
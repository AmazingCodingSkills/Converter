package com.currency.converter.features.calculator.presenter

import com.currency.converter.base.CurrencyRatesRepository
import com.currency.converter.base.NetworkRepository
import com.currency.converter.features.calculator.view.CalculatorView
import com.example.converter.R

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
        try {
            if (input.isNotEmpty()) {
                onCurrencyConverted(from, input, to)
            }
            if (input == "0." || input == "0") {
                view?.clearTo()
            } else {
                view?.clearFrom()
            }
        } catch (message: Throwable) {
            view?.showToast(R.string.message_for_exception_calculator.toString())
        }
    }

    fun onTextInputChangedTwo(input: String) {
        try {
            if (input.isNotEmpty()) {
                onCurrencyConverted(to, input, from)
            }
            if (input == "0.") {
                view?.clearFrom()
            } else {
                view?.clearTo()
            }
        } catch (message: Throwable) {
            view?.showToast(R.string.message_for_exception_calculator.toString())
        }
    }

    fun onCurrencyConverted(
        baseCurrencyCode: String,
        input: String,
        referenceCurrencyCode: String
    ) {
        val value = input.toDouble()
        onDialogWarning()
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
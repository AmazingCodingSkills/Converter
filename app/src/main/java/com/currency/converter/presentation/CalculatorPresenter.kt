package com.currency.converter.presentation

import com.currency.converter.base.CurrencyRatesRepositoryImpl
import com.currency.converter.data.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CalculatorPresenter(
    private val networkRepository: NetworkRepository,
    private val currencyRatesRepository: CurrencyRatesRepositoryImpl
) {

    private var view: CalculatorView? = null
    private var from = "USD"
    private var to = "EUR"
    private var newImpl: Boolean = true

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
        GlobalScope.launch(Dispatchers.Main) {
            if (!networkRepository.isInternetUnavailable()) {
                if (newImpl) {
                    GlobalScope.launch(Dispatchers.Main) {
                        val currentRates = currencyRatesRepository.getCurrentRatesCoroutine(
                            baseCurrencyCode,
                            referenceCurrencyCode
                        )
                        val result = value * currentRates
                        if (from == baseCurrencyCode && to == referenceCurrencyCode) {
                            view?.setResultOneConversion(result)
                        } else {
                            view?.setResultTwoConversion(result)
                        }
                    }
                } else {
                    currencyRatesRepository.getCurrentRates(
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

            } else {
                view?.showDialog()
            }
        }
    }

    fun onStarted() {
        view?.setCurrencies(to, from)
    }

    fun onDialogWarning() {
        GlobalScope.launch(Dispatchers.Main) {
            if (networkRepository.isInternetUnavailable()) {
                view?.showDialog()
            }
        }

    }

    fun detachView() {
        this.view = null
    }

}
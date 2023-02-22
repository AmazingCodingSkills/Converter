package com.currency.converter.features.calculator.presentation

import com.currency.converter.base.network.NetworkRepository
import com.currency.converter.features.calculator.domain.UseCaseGetCurrentRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CalculatorPresenter(
    private val networkRepository: NetworkRepository,
    private val useCaseGetCurrentRates: UseCaseGetCurrentRates
) {

    private var view: CalculatorView? = null
    private var from = "USD"
    private var to = "EUR"

    fun attachView(view: CalculatorView) {
        this.view = view
    }

    fun setFrom(selectedCurrencyFromOne: String, input: String) {
        from = selectedCurrencyFromOne
        view?.setDefaultValueFrom(from)
        if (input.isNotEmpty()) {
            onCurrencyConverted(
                selectedCurrencyFromOne,
                input,
                to
            )

        }
    }

    fun setTo(selectedCurrencyFromTwo: String, input: String) {
        to = selectedCurrencyFromTwo
        view?.setDefaultValueTo(to)
        if (input.isNotEmpty()) {
            onCurrencyConverted(
                selectedCurrencyFromTwo,
                input,
                from
            )
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
                GlobalScope.launch(Dispatchers.Main) {
                    val currentRates = useCaseGetCurrentRates.getCurrentRate(
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
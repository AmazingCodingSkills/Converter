package com.currency.converter.features.calculator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.converter.base.network.NetworkRepository
import com.currency.converter.features.calculator.domain.UseCaseGetCurrentRates
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CalculatorViewModel(
    private val networkRepository: NetworkRepository,
    private val useCaseGetCurrentRates: UseCaseGetCurrentRates
) : ViewModel() {

    private var from = "USD"
    private var to = "EUR"

    private val viewState = MutableStateFlow<CalculatorViewState>(CalculatorViewState.Content(resultFrom = null, resultTo = null, from = from, to = to))
    val state: StateFlow<CalculatorViewState> = viewState.asStateFlow()

    private val viewEvents = Channel<CalculatorViewEvent>(Channel.UNLIMITED)
    val events: Flow<CalculatorViewEvent> = viewEvents.receiveAsFlow()



    fun handleAction(action: CalculatorViewAction) {
        when (action) {
            is CalculatorViewAction.CurrencyConvertedOne -> onTextInputChanged(action.input, from, to)
            is CalculatorViewAction.CurrencyConvertedTwo -> onTextInputChanged(action.input, to, from)
            is CalculatorViewAction.CurrencySetFrom -> setFrom(action.currencyCode, action.input)
            is CalculatorViewAction.CurrencySetTo -> setTo(action.currencyCode, action.input)
            is CalculatorViewAction.InternetError -> internetErrorStart()

        }
    }

    private fun setFrom(selectedCurrencyFromOne: String, input: String) {
        from = selectedCurrencyFromOne
        viewState.value = CalculatorViewState.Content(resultFrom = null, resultTo = null, from = from, to = to)
        if (input.isNotEmpty()) {
            convertCurrency(
                selectedCurrencyFromOne,
                input,
                to
            )
        }
    }


    private fun setTo(selectedCurrencyFromTwo: String, input: String) {
        to = selectedCurrencyFromTwo
        viewState.value = CalculatorViewState.Content(resultFrom = null, resultTo = null, from = from, to = to)
        if (input.isNotEmpty()) {
            convertCurrency(
                selectedCurrencyFromTwo,
                input,
                from
            )
        }
    }


    private fun onTextInputChanged(input: String, from: String, to: String) {
        if (isInputValid(input)) {
            convertCurrency(from, input, to)
        } else {
            viewState.value = CalculatorViewState.Empty
        }
    }

    private fun isInputValid(input: String): Boolean =
        input.isNotEmpty() && input != "."

    private fun convertCurrency(
        baseCurrencyCode: String,
        input: String,
        referenceCurrencyCode: String
    ) {
        val value = input.toDouble()
        viewModelScope.launch {
            if (!networkRepository.isInternetUnavailable()) {
                try {
                    val currentRates = useCaseGetCurrentRates.getCurrentRate(
                        baseCurrencyCode,
                        referenceCurrencyCode
                    )
                    val result = value * currentRates
                    if (from == baseCurrencyCode && to == referenceCurrencyCode) {
                        viewState.value = CalculatorViewState.Content(
                            resultFrom = result,
                            resultTo = null,
                            baseCurrencyCode,
                            referenceCurrencyCode
                        )
                    } else {
                        viewState.value = CalculatorViewState.Content(
                            resultFrom = null,
                            resultTo = result,
                            referenceCurrencyCode,
                            baseCurrencyCode
                        )
                    }
                } catch (e: Exception) {
                    viewEvents.trySend(CalculatorViewEvent.ShowErrorDialog)
                }
            } else {
                viewEvents.trySend(CalculatorViewEvent.ShowErrorDialog)
            }
        }
    }


    private fun internetErrorStart() {
        viewModelScope.launch {
            if (networkRepository.isInternetUnavailable()) {
                viewEvents.trySend(CalculatorViewEvent.ShowErrorDialog)
            }
        }
    }
}

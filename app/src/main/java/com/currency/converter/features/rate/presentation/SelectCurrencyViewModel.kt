package com.currency.converter.features.rate.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectCurrencyViewModel(): ViewModel() {

    private val viewState = MutableStateFlow<SelectCurrencyViewState>(SelectCurrencyViewState.Content)
    val state: StateFlow<SelectCurrencyViewState> = viewState.asStateFlow()

    fun handleAction(action: SelectCurrencyViewAction) {
    }

}
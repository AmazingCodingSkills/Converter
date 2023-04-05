package com.example.rate.presentation.selectcurrency

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectCurrencyViewModel(
    referenceCurrency: String,
    baseCurrency: String
) : ViewModel() {

    init {
        Log.d("TestDI", "$referenceCurrency + $baseCurrency")
    }

    private val viewState =
        MutableStateFlow<SelectCurrencyViewState>(SelectCurrencyViewState.Content)
    val state: StateFlow<SelectCurrencyViewState> = viewState.asStateFlow()



}
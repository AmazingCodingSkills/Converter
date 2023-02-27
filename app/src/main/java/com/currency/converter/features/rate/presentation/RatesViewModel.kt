package com.currency.converter.features.rate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.converter.base.network.NetworkRepository
import com.currency.converter.features.rate.domain.SelectedCurrencyRepository
import com.currency.converter.features.rate.domain.UseCaseGetRates
import com.currency.converter.features.rate.presentation.RatesViewAction.UpdateCurrency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RatesViewModel(
    private val networkRepository: NetworkRepository,
    private val selectedCurrencyRepository: SelectedCurrencyRepository,
    private val useCaseGetRates: UseCaseGetRates
) : ViewModel() {

    private val viewState = MutableStateFlow<RatesViewState>(RatesViewState.Loading)
    val state: StateFlow<RatesViewState> = viewState.asStateFlow()

    init {
        loadRates()
    }

    fun handleAction(action: RatesViewAction) {
        when (action) {
            RatesViewAction.SelectCurrency -> loadRates()
            is UpdateCurrency -> loadRates()
        }
    }

    private fun loadRates() {
        // вот тут есть вопрос, после перехода между фрагментами
        // все ломается  на месте запуска корутины
        // наверное потому что прошлая корутин еще не завершилась
        // Global Scope эту проблему решает
        // получается, что при переходе между фрагментами, корутина закрывается
        // но нашел инфу о том, что если мы говорим, о пересоздании фрагмента
        // то viewModel уничтожается, соответсвенно ее нужно вызывать занаво
        // но почему-то это не работает
        viewModelScope.launch {
            viewState.value = RatesViewState.Loading
            try {
                val allCurrency = selectedCurrencyRepository.selectedCurrency()
                if (allCurrency != null) {
                    val base = allCurrency.baseCurrency
                    if (!networkRepository.isInternetUnavailable()) {
                        val rates = useCaseGetRates(base)
                        val icon = allCurrency.icon
                        viewState.value = RatesViewState.Content(rates, icon)
                    } else {
                        viewState.value = RatesViewState.Error
                    }
                }
            } catch (throwable: Throwable) {
                viewState.value = RatesViewState.Error
            }
        }
    }
}






package com.example.rate.presentation.latestrates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.converter.core.data.favouritecurrencymodel.FavouriteCurrencyRepository
import com.converter.core.presentation.networkfragment.NetworkRepository
import com.example.rate.presentation.latestrates.RatesViewAction.UpdateCurrency
import com.example.rate.domain.UseCaseGetRates
import com.example.rate.presentation.selectcurrency.SelectedCurrencyRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RatesViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val selectedCurrencyRepository: SelectedCurrencyRepository,
    private val useCaseGetRates: UseCaseGetRates,
    private val favouriteCurrencyRepository: FavouriteCurrencyRepository
) : ViewModel() {

    private val viewState = MutableStateFlow<RatesViewState>(RatesViewState.Loading)
    val state: StateFlow<RatesViewState> = viewState.asStateFlow()

    private val viewEvents = Channel<RatesViewEvent>(Channel.UNLIMITED)
    val events: Flow<RatesViewEvent> = viewEvents.receiveAsFlow()


    init {
        loadRates()
        favouriteCurrencyRepository.onUpdate().onEach {
            loadRates()
        }.launchIn(viewModelScope)
    }

    fun handleAction(action: RatesViewAction) {
        when (action) {
            RatesViewAction.SelectCurrency -> loadRates()
            is UpdateCurrency -> {
                loadRates()
            }
        }
    }

    fun loadRates() {
        viewModelScope.launch {
            viewState.value = RatesViewState.Loading
            try {
                val allCurrency = selectedCurrencyRepository.selectedCurrency()
                if (allCurrency != null) {
                    val base = allCurrency.baseCurrency
                    if (networkRepository.isInternetAvailable()) {
                        val rates = useCaseGetRates(base)
                        val icon = allCurrency.icon
                        viewState.value = RatesViewState.Content(rates, icon)
                    } else {
                        viewState.tryEmit(RatesViewState.Empty)
                        viewEvents.trySend(RatesViewEvent.ShowErrorDialog)
                    }
                } else {
                    viewEvents.trySend(RatesViewEvent.ShowErrorDialog)
                }
            } catch (throwable: Throwable) {
                viewEvents.trySend(RatesViewEvent.ShowErrorDialog)
            }
        }
    }
}






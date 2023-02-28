package com.currency.converter.features.rate.presentation

import com.currency.converter.base.currency.CurrencyRatesRepositoryImpl
import com.currency.converter.base.network.NetworkRepositoryImpl
import com.currency.converter.base.SelectedCurrencyRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RatesPresenter(
    private val networkRepositoryImpl: NetworkRepositoryImpl,
    private val selectedCurrencyRepository: SelectedCurrencyRepositoryImpl,
    private val currencyRatesRepository: CurrencyRatesRepositoryImpl
) {

    private var view: RateView? = null

    fun attachView(view: RateView) {
        this.view = view
    }

    fun onRefreshed() {
        onSavedCurrencyGated()
    }

    fun onSelectedCurrencyShowed(base: String, icon: Int) {
        view?.showProgress() // корутины
        GlobalScope.launch(Dispatchers.IO) {
            if (!networkRepositoryImpl.isInternetUnavailable()) {
                try {
                    val rates = currencyRatesRepository.getRatesCoroutine(base)
                    withContext(Dispatchers.Main) {
                        view?.showRates(rates)
                        view?.hideProgress()
                        view?.showIcon(icon)
                        view?.showRefreshing(false)
                    }
                } catch (throwable: Throwable) {
                    withContext(Dispatchers.Main) {
                        view?.showToast(throwable.toString())
                        view?.hideProgress()
                        view?.showIcon(icon)
                        view?.showRefreshing(false)
                    }
                }
            } else {
                view?.showDialogWarning()
            }
        }
    }

    fun onSavedCurrencyGated() {
        GlobalScope.launch(Dispatchers.Main) {
            val allCurrency = selectedCurrencyRepository.selectedCurrency()
            if (allCurrency != null) {
                onSelectedCurrencyShowed(
                    allCurrency.baseCurrency,
                    allCurrency.icon
                )
                view?.showIcon(allCurrency.icon)
            }
        }
    }

    fun detachView() {
        this.view = null
    }
}


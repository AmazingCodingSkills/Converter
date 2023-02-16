package com.currency.converter.features.rate.presenter

import com.currency.converter.base.CurrencyRatesRepository
import com.currency.converter.base.NetworkRepository
import com.currency.converter.features.rate.view.RateView


class RatesPresenter(private val networkRepository: NetworkRepository) {

    private var view: RateView? = null

    fun attachView(view: RateView) {
        this.view = view
    }

    fun onRefreshed() {
        var checkPerformanceFunction = true
        view?.showRefreshing(checkPerformanceFunction)
        if (checkPerformanceFunction) {
            onSavedCurrencyGated()
        }
        checkPerformanceFunction = false
        if (checkPerformanceFunction == false) {
            view?.showRefreshing(checkPerformanceFunction)
        }

        view?.hideProgress()
    }

    fun onSelectedCurrencyShowed(base: String, icon: Int) {
        view?.showProgress()
        if (!networkRepository.isInternetUnavailable()) {
            CurrencyRatesRepository.getRates(base, onFailure = {
                view?.showToast(it.toString())
            }) {
                view?.showRates(it)
                view?.hideProgress()
            }
        } else {
            view?.showDialogWarning()
        }
    }

    fun onSavedCurrencyGated() {
        val allCurrency = CurrencyRatesRepository.selectedCountries
        if (allCurrency != null) {
            onSelectedCurrencyShowed(
                allCurrency.baseCurrency,
                allCurrency.icon
            )
            view?.showIcon(allCurrency.icon)
        }
    }

    fun detachView() {
        this.view = null
    }
}


package com.currency.converter.presentation

import android.util.Log
import com.currency.converter.base.CurrencyRatesRepositoryImpl
import com.currency.converter.data.NetworkRepository
import com.currency.converter.base.SelectedCurrencyRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RatesPresenter(private val networkRepository: NetworkRepository, private val selectedCurrencyRepository: SelectedCurrencyRepositoryImpl, private val currencyRatesRepository: CurrencyRatesRepositoryImpl) {

    private var view: RateView? = null
    private var newImpl: Boolean = true

    fun attachView(view: RateView) {
        this.view = view
    }

    fun onRefreshed() {
        onSavedCurrencyGated()
    }

    fun onSelectedCurrencyShowed(base: String, icon: Int) {
        if (newImpl) { // фича флаг
            view?.showProgress() // корутины
            GlobalScope.launch (Dispatchers.Main){
                if (!networkRepository.isInternetUnavailable()) {
                    Log.d("TAG","1" + Thread.currentThread().name) // посмотреть треды, так как ретрофит тоже их меняет
                    GlobalScope.launch(Dispatchers.Main) {
                        try {
                            GlobalScope.launch (Dispatchers.Main){
                                Log.d("TAG","2" + Thread.currentThread().name)
                                val rates = currencyRatesRepository.getRatesCoroutine(base)
                                Log.d("TAG","3" + Thread.currentThread().name)
                                view?.showRates(rates)
                                view?.hideProgress()
                                view?.showIcon(icon)
                                view?.showRefreshing(false)
                            }
                        } catch (throwable: Throwable) {
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
        } else { // старая реализация через колбеки
            view?.showProgress()
            GlobalScope.launch (Dispatchers.Main){
                if (!networkRepository.isInternetUnavailable()) {
                    currencyRatesRepository.getRates(base, onFailure = {
                        view?.showToast(it.toString())
                    }) {
                        view?.showRates(it)
                        view?.hideProgress()
                        view?.showIcon(icon)
                        view?.showRefreshing(false)
                    }
                } else {
                    view?.showDialogWarning()
                }
            }
        }
    }

    fun onSavedCurrencyGated() {

        if (newImpl){
            GlobalScope.launch (Dispatchers.Main){
                val allCurrency = selectedCurrencyRepository.selectedCurrency()
                if (allCurrency != null) {
                    onSelectedCurrencyShowed(
                        allCurrency.baseCurrency,
                        allCurrency.icon
                    )
                    view?.showIcon(allCurrency.icon)
                }
            }
        } else{
            val allCurrency = currencyRatesRepository.selectedCurrency
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


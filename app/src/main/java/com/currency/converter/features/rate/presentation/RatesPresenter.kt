package com.currency.converter.features.rate.presentation

import android.util.Log
import com.currency.converter.base.CurrencyRatesRepositoryImpl
import com.currency.converter.base.NetworkRepositoryImpl
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
                Log.d(
                    "TAG",
                    "1" + Thread.currentThread().name
                ) // посмотреть треды, так как ретрофит тоже их меняет
                try {
                    Log.d("TAG", "2" + Thread.currentThread().name)
                    val rates = currencyRatesRepository.getRatesCoroutine(base)
                    Log.d("TAG", "3" + Thread.currentThread().name)

                    withContext(Dispatchers.Main) {
                        Log.d("TAG", "4" + Thread.currentThread().name)
                        view?.showRates(rates)
                        view?.hideProgress()
                        view?.showIcon(icon)
                        view?.showRefreshing(false)
                    }
                } catch (throwable: Throwable) {
                    withContext(Dispatchers.Main) {
                        Log.d("TAG", "5" + Thread.currentThread().name)
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


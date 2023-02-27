package com.currency.converter.features.rate.presentation


/*class RatesPresenter(
    private val networkRepository: NetworkRepository,
    private val selectedCurrencyRepository: SelectedCurrencyRepository,
    private val useCaseGetRates: UseCaseGetRates
) {

    private var view: RateView? = null

    fun attachView(view: RateView) {
        this.view = view
    }

   *//* fun onRefreshed() {
        onSavedCurrencyGated()
    }*//*

    fun onSelectedCurrencyShowed(base: String, icon: Int) {
        view?.showProgress() // корутины
        GlobalScope.launch(Dispatchers.IO) {
            if (!networkRepository.isInternetUnavailable()) {
                try {
                    val rates = useCaseGetRates(base)
                    withContext(Dispatchers.Main) {
                        view?.showRates(rates)
                        view?.hideProgress()
                        view?.showIcon(icon)
                      //  view?.showRefreshing(false)
                    }
                } catch (throwable: Throwable) {
                    withContext(Dispatchers.Main) {
                        view?.showToast(throwable.toString())
                        view?.hideProgress()
                        view?.showIcon(icon)
                        //view?.showRefreshing(false)
                    }
                }
            } else {
               // view?.showDialogWarning()
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
}*/


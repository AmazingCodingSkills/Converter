package com.currency.converter.features.rate.presentation

import com.currency.converter.features.rate.domain.RateItem

interface RateView {

    fun showRates(list: List<RateItem>?)
    fun changeRates(base: String, icon: Int)
    fun showIcon(icon: Int)
    fun showRefreshing(refreshing: Boolean)
    fun showDialogWarning()
    fun showProgress()
    fun hideProgress()
    fun showToast(message: String)

}
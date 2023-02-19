package com.currency.converter.domain

import com.currency.converter.features.rate.countryname.CountryModel

interface SelectedCurrencyInterface {
    suspend fun selectedCurrency(): CountryModel?
}
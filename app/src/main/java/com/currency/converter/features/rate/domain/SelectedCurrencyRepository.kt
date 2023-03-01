package com.currency.converter.features.rate.domain

import com.currency.converter.features.rate.countryname.CountryModel

interface SelectedCurrencyRepository {

    suspend fun selectedCurrency(): CountryModel?

}
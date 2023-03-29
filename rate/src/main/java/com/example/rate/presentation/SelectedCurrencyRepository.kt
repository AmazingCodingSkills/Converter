package com.example.rate.presentation

import com.currency.converter.features.rate.countryname.CountryModel

interface SelectedCurrencyRepository {

    suspend fun selectedCurrency(): CountryModel?

}
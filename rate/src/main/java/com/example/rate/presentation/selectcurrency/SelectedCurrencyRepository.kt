package com.example.rate.presentation.selectcurrency

import com.converter.core.country.data.CountryModel

interface SelectedCurrencyRepository {

    suspend fun selectedCurrency(): CountryModel?

}
package com.example.rate.presentation.selectcurrency

import com.converter.core.data.favouritecurrencymodel.CountryModel

interface SelectedCurrencyRepository {

    suspend fun selectedCurrency(): CountryModel?

}
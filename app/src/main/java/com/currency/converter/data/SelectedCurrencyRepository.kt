package com.currency.converter.base

import com.currency.converter.ConverterApplication
import com.currency.converter.domain.SelectedCurrencyInterface
import com.currency.converter.features.rate.countryname.CountryModel


class SelectedCurrencyRepositoryImpl() : SelectedCurrencyInterface {
    override suspend fun selectedCurrency() : CountryModel? {
        val selectedCurrency = ConverterApplication.PreferencesManager.get<CountryModel>(
            ConverterApplication.PreferencesManager.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
        )
        return selectedCurrency
    }
}
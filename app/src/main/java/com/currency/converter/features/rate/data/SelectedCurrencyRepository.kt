package com.currency.converter.base

import com.currency.converter.ConverterApplication
import com.currency.converter.features.rate.countryname.CountryModel
import com.currency.converter.features.rate.domain.SelectedCurrencyRepository


class SelectedCurrencyRepositoryImpl() : SelectedCurrencyRepository {
    override suspend fun selectedCurrency(): CountryModel? {
        return ConverterApplication.PreferencesManager.get<CountryModel>(
            ConverterApplication.PreferencesManager.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
        )
    }
}
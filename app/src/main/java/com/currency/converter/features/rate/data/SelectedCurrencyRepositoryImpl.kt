package com.currency.converter.base

import com.currency.converter.ConverterApplication
import com.currency.converter.features.rate.countryname.CountryModel
import com.currency.converter.features.rate.domain.SelectedCurrencyRepository
import javax.inject.Inject


class SelectedCurrencyRepositoryImpl @Inject constructor() : SelectedCurrencyRepository {

    override suspend fun selectedCurrency(): CountryModel? {
        return ConverterApplication.PreferencesManager.get<CountryModel>(
            ConverterApplication.PreferencesManager.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
        )
    }

}
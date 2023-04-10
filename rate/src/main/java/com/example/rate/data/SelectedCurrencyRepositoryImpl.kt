package com.example.rate.data


import com.converter.core.Constants.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
import com.converter.core.PreferencesManager
import com.converter.core.country.data.CountryModel
import com.example.rate.presentation.selectcurrency.SelectedCurrencyRepository
import javax.inject.Inject

internal class SelectedCurrencyRepositoryImpl @Inject constructor() : SelectedCurrencyRepository {

    override suspend fun selectedCurrency(): CountryModel? {
        return PreferencesManager.get<CountryModel>(
            BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
        )
    }

}
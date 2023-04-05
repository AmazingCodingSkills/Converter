package com.example.rate.presentation


import com.converter.core.data.Constants.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
import com.converter.core.PreferencesManager
import com.converter.core.data.favouritecurrencymodel.CountryModel
import com.example.rate.presentation.selectcurrency.SelectedCurrencyRepository
import javax.inject.Inject

internal class SelectedCurrencyRepositoryImpl @Inject constructor() : SelectedCurrencyRepository {

    override suspend fun selectedCurrency(): CountryModel? {
        return PreferencesManager.get<CountryModel>(
            BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
        )
    }

}
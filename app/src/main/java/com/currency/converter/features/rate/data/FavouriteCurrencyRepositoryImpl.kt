package com.currency.converter.features.rate.data

import com.currency.converter.ConverterApplication
import com.currency.converter.base.favoritemodel.CurrencyItem
import com.currency.converter.features.rate.domain.FavouriteCurrencyRepository
import javax.inject.Inject

class FavouriteCurrencyRepositoryImpl @Inject constructor() : FavouriteCurrencyRepository {

    override suspend fun favouritesCurrency(): List<CurrencyItem> {
        return ConverterApplication.appComponent.providesRoom().getCurrencyItem()
    }
}
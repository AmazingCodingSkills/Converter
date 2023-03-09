package com.currency.converter.features.rate.domain

import com.currency.converter.base.favoritemodel.CurrencyItem

interface FavouriteCurrencyRepository {

    suspend fun favouritesCurrency(): List<CurrencyItem>?

}
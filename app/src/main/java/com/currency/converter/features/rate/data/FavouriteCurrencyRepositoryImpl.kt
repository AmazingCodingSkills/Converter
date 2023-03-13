package com.currency.converter.features.rate.data

import com.currency.converter.base.favoritemodel.CurrencyItem
import com.currency.converter.base.room.CurrencyItemDao
import com.currency.converter.features.rate.domain.FavouriteCurrencyRepository
import javax.inject.Inject

class FavouriteCurrencyRepositoryImpl @Inject constructor(private val currencyItemDao: CurrencyItemDao) : FavouriteCurrencyRepository {

    override suspend fun favouritesCurrency(): List<CurrencyItem> {
        return currencyItemDao.getFavoriteItems().map { CurrencyItem(it.id,it.currencyName,it.isFavorite) }
    }


}
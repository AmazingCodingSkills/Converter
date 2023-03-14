package com.currency.converter.features.rate.data

import com.currency.converter.base.favoritemodel.CurrencyItem
import com.currency.converter.base.room.CurrencyItemDao
import com.currency.converter.base.room.Favorite
import com.currency.converter.features.rate.domain.FavouriteCurrencyRepository
import javax.inject.Inject

class FavouriteCurrencyRepositoryImpl @Inject constructor(private val currencyItemDao: CurrencyItemDao) :
    FavouriteCurrencyRepository {

    override suspend fun favouritesCurrency(): List<CurrencyItem> {
        return currencyItemDao.getFavoriteItems()
            .map { CurrencyItem(it.id, it.currencyName, it.isFavorite) }
    }

    override suspend fun getAll(): List<Favorite> {
        return currencyItemDao.getAll()
    }

    override suspend fun getFavoriteItems(): List<Favorite> {
        return currencyItemDao.getFavoriteItems()
    }

    suspend fun insert(favorite: Favorite) {
        currencyItemDao.insertFavorite(favorite)
    }

    override suspend fun update(id: String, update: Boolean) {
        currencyItemDao.update(id, update)
    }
}

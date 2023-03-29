package com.converter.core.favouritecurrency


import com.converter.core.favoritemodel.CurrencyItem
import com.converter.core.room.CurrencyItemDao
import com.converter.core.room.Favorite
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

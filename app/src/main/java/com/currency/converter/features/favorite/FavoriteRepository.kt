package com.currency.converter.features.favorite

import com.currency.converter.base.room.CurrencyItemDao
import com.currency.converter.base.room.Favorite
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val favoriteDao: CurrencyItemDao) {

    val allFavorites: Flow<List<Favorite>> = favoriteDao.getAllFavorites()

    val favorites: Flow<List<Favorite>> = favoriteDao.getFavorites()

    suspend fun insert(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite)
    }
}
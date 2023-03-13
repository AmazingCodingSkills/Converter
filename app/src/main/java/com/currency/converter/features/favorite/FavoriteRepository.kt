package com.currency.converter.features.favorite

import com.currency.converter.base.room.CurrencyItemDao
import com.currency.converter.base.room.Favorite

class FavoriteRepository(private val favoriteDao: CurrencyItemDao) {

    suspend fun getAll(): List<Favorite>{
        return favoriteDao.getAll()
    }

    suspend fun getFavoriteItems(): List<Favorite> {
        return favoriteDao.getFavoriteItems()
    }

    suspend fun insert(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite)
    }

    suspend fun update(id: String, update: Boolean){
        favoriteDao.update(id,update)
    }
}
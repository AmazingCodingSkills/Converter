package com.converter.core.favouritecurrency

import com.converter.core.favoritemodel.CurrencyItem
import com.converter.core.room.Favorite

interface FavouriteCurrencyRepository {

    suspend fun favouritesCurrency(): List<CurrencyItem>?


    suspend fun getAll(): List<Favorite>


    suspend fun getFavoriteItems(): List<Favorite>


    suspend fun update(id: String, update: Boolean)

}
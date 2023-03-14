package com.currency.converter.features.rate.domain

import com.currency.converter.base.favoritemodel.CurrencyItem
import com.currency.converter.base.room.Favorite

interface FavouriteCurrencyRepository {

    suspend fun favouritesCurrency(): List<CurrencyItem>?


    suspend fun getAll(): List<Favorite>


    suspend fun getFavoriteItems(): List<Favorite>


    suspend fun update(id: String, update: Boolean)

}
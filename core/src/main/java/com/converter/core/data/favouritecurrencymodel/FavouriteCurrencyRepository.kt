package com.converter.core.data.favouritecurrencymodel

import com.converter.core.data.currencymodel.CurrencyItem
import com.converter.core.data.room.Favorite
import kotlinx.coroutines.flow.Flow

interface FavouriteCurrencyRepository {

    fun onUpdate(): Flow<Unit>

    fun onUpdateItem(): Flow<CurrencyItem>

    suspend fun favouritesCurrency(): List<CurrencyItem>?


    suspend fun getAll(): List<Favorite>


    suspend fun getFavoriteItems(): List<Favorite>


    suspend fun update(id: String, update: Boolean)

}
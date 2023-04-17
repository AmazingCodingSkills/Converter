package com.converter.core.favorite.domain

import com.converter.core.currency.domain.CurrencyItem
import com.converter.core.favorite.data.Favorite
import kotlinx.coroutines.flow.Flow

interface FavouriteCurrencyRepository {

    fun onUpdate(): Flow<Unit>

    suspend fun favouritesCurrency(): List<CurrencyItem>?

    suspend fun getAll(): List<Favorite>

    suspend fun getFavoriteItems(): List<Favorite>

    suspend fun update(id: String, update: Boolean)

    suspend fun insertAll(favorite: List<Favorite>)

    suspend fun loadRatesInInitDB()

}
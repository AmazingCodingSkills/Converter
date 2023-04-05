package com.converter.core.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("SELECT * FROM Favorite")
    suspend fun getAll(): List<Favorite>

    @Query("SELECT * FROM Favorite WHERE isFavorite = 1")
    suspend fun getFavoriteItems(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(favoriteList: List<Favorite>)

    @Query("UPDATE Favorite SET isFavorite = :update WHERE id = :id")
    suspend fun update(id: String, update: Boolean)

}
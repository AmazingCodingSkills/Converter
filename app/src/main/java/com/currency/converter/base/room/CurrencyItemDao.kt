package com.currency.converter.base.room

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
/*



    @Query("SELECT * FROM Favorite WHERE id = :id")
    fun getById(id: String): Favorite

    @Update
    fun update(entity: Favorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(favoriteList: List<Favorite>)

    @Query("SELECT * FROM Favorite")
    suspend fun getFavoriteItem(): List<Favorite>

    @Query("SELECT * FROM Favorite WHERE isFavorite = :update")
    suspend fun getItemsByUpdate(update: Boolean): List<Favorite>

    @Update
    fun updateFavoriteItem(favoriteList: List<Favorite>)*/

    /*@Insert
    fun insertFavoriteItem(favoriteItem: Favorite)





    @Delete
    fun deleteCurrencyItem(favoriteItem: Favorite)

    @Query("SELECT * FROM Favorite")
    suspend fun getFavoriteItem(): List<Favorite>

    @Query("SELECT * FROM Favorite WHERE isFavorite = :update")
    suspend fun getItemsByUpdate(update: Boolean): List<Favorite>

    @Query("SELECT * FROM Favorite WHERE isFavorite = 1")
    suspend fun getFavoriteItems(): List<Favorite>*/

    /*@Query("SELECT * FROM Favorite WHERE currencyName = :currencyName")
    suspend fun getCurrencyItemByName(currencyName: String): CurrencyItem?*/

    //@Query("UPDATE * FROM CurrencyItem WHERE ")

    /*@Transaction
    suspend fun updateCurrencyItems(currencyItems: List<CurrencyItem>) : List<CurrencyItem>  {
        currencyItems.forEach { currencyItem ->
            updateCurrencyItem(currencyItem)
        }
        return currencyItems
    }*/

}
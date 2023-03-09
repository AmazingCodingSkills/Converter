package com.currency.converter.base.room

import androidx.room.*
import com.currency.converter.base.favoritemodel.CurrencyItem


@Dao
interface CurrencyItemDao {

    @Insert
    fun insertCurrencyItem(currencyItem: CurrencyItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<CurrencyItem>)

    @Update
    fun updateCurrencyItem(currencyItem: CurrencyItem)

    @Delete
    fun deleteCurrencyItem(currencyItem: CurrencyItem)

    @Query("SELECT * FROM CurrencyItem")
    suspend fun getCurrencyItem(): List<CurrencyItem>

  /*  @Query("SELECT * FROM CurrencyItem WHERE is_updated = :update")
    suspend fun getItemsByUpdate(update: Boolean): List<CurrencyItem>*/

    @Query("SELECT * FROM CurrencyItem WHERE isFavorite = 1")
    suspend fun getFavoriteCurrencyItems(): List<CurrencyItem>

    @Transaction
    suspend fun updateCurrencyItems(currencyItems: List<CurrencyItem>) : List<CurrencyItem>  {
        currencyItems.forEach { currencyItem ->
            updateCurrencyItem(currencyItem)
        }
        return currencyItems
    }

}
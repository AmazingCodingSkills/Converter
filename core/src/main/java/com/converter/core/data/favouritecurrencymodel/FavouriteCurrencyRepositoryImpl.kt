package com.converter.core.data.favouritecurrencymodel


import com.converter.core.data.currencymodel.CurrencyItem
import com.converter.core.data.room.CurrencyItemDao
import com.converter.core.data.room.Favorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FavouriteCurrencyRepositoryImpl @Inject constructor(private val currencyItemDao: CurrencyItemDao) :
    FavouriteCurrencyRepository {

    private val onEvent = MutableSharedFlow<Unit>(replay = 1)

    private val onEventItem = MutableSharedFlow<CurrencyItem>(replay = 1)

    override fun onUpdate(): Flow<Unit> = onEvent


    override fun onUpdateItem(): Flow<CurrencyItem> = onEventItem

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
        onEvent.tryEmit(Unit)
        onEventItem.tryEmit(CurrencyItem(id,id,update))
    }
}

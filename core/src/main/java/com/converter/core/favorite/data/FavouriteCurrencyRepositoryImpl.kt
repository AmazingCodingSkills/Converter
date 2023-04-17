package com.converter.core.favorite.data


import com.converter.core.Constants.ALL_CURRENCY_KEY
import com.converter.core.PreferencesManager
import com.converter.core.currency.domain.CurrencyItem
import com.converter.core.favorite.FavouriteMapper
import com.converter.core.favorite.domain.FavouriteCurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FavouriteCurrencyRepositoryImpl @Inject constructor(private val currencyItemDao: CurrencyItemDao) :
    FavouriteCurrencyRepository {

    private val onEvent = MutableSharedFlow<Unit>(replay = 1)

    override fun onUpdate(): Flow<Unit> = onEvent


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

    override suspend fun update(id: String, update: Boolean) {
        currencyItemDao.update(id, update)
        onEvent.tryEmit(Unit)
    }

    override suspend fun insertAll(favorite: List<Favorite>) {
        currencyItemDao.insertAll(favorite)
    }

    override suspend fun loadRatesInInitDB() {
        if (getAll().isEmpty()) {
            val allRates = PreferencesManager.get<List<CurrencyItem>>(ALL_CURRENCY_KEY)
            if (allRates != null && allRates.isNotEmpty()) {
                insertAll(FavouriteMapper.map(allRates))
            }
        }

    }
}

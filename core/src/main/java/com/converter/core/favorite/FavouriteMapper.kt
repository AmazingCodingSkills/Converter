package com.converter.core.favorite

import com.converter.core.currency.domain.CurrencyItem
import com.converter.core.favorite.data.Favorite

object FavouriteMapper {

    fun map(currencyList: List<CurrencyItem>): List<Favorite> {
        return currencyList.map {
            Favorite(
                it.id,
                it.currencyName,
                it.isFavorite
            )
        }
    }
}
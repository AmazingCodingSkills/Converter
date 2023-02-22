package com.currency.converter.features.rate.domain

import com.currency.converter.base.currency.CurrencyRatesRepository


class UseCaseGetRates(
    private val favouriteCurrencyRepository: FavouriteCurrencyRepository,
    private val currencyRatesRepository: CurrencyRatesRepository,
) {
    suspend fun getRates(base: String): List<RateItem> {
        val favorites =
            favouriteCurrencyRepository.favouritesCurrency()?.filter { it.isFavorite }.orEmpty()
        val response = currencyRatesRepository.getLatestApiResultCoroutine(base)
        val favoriteCurrencies = response?.filter { item ->
            favorites.find { favorite -> item.referenceCurrency.name == favorite.id } != null
        }.orEmpty()
        return favoriteCurrencies.ifEmpty { response }
    }
}



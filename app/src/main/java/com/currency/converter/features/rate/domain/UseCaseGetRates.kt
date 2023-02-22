package com.currency.converter.features.rate.domain

import com.currency.converter.base.currency.CurrencyRatesRepository

class UseCaseGetRates(
    private val favouriteCurrencyRepository: FavouriteCurrencyRepository,
    private val currencyRatesRepository: CurrencyRatesRepository,
) {
    suspend operator fun invoke(base: String): List<RateItem> {
        val favorites =
            favouriteCurrencyRepository.favouritesCurrency()?.filter { it.isFavorite }.orEmpty()
        val response = currencyRatesRepository.getLatestApiResultCoroutine(base)
        val favoriteCurrencies = response.filter { item ->
            favorites.find { favorite -> item.referenceCurrency.name == favorite.id } != null
        }
        return favoriteCurrencies.ifEmpty { response }
    }
}



package com.currency.converter.features.rate.domain

import com.converter.core.currency.CurrencyRatesRepository
import com.converter.core.currency.RateItem
import javax.inject.Inject

class UseCaseGetRates @Inject constructor(
    private val favouriteCurrencyRepository: FavouriteCurrencyRepository,
    private val currencyRatesRepository: CurrencyRatesRepository
) {
    suspend operator fun invoke(base: String): List<RateItem> {
        val favorites =
            favouriteCurrencyRepository.favouritesCurrency().orEmpty()
        val response = currencyRatesRepository.getLatestApiResult(base)
        val favoriteCurrencies = response.filter { item ->
            favorites.find { favorite -> item.referenceCurrency.name == favorite.id } != null
        }
        return favoriteCurrencies.ifEmpty { response }
    }
}




package com.example.rate.domain

import com.converter.core.data.currencyrate.CurrencyRatesRepository
import com.converter.core.data.favouritecurrencymodel.FavouriteCurrencyRepository
import com.converter.core.data.currencyrate.RateItem
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




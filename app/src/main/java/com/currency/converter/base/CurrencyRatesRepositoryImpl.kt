package com.currency.converter.base

import com.currency.converter.ConverterApplication
import com.currency.converter.features.rate.domain.RateItem


class CurrencyRatesRepositoryImpl(private val rateItemMapper: RateItemMapper) : CurrencyRatesRepository {

    override suspend fun getLatestApiResultCoroutine(base: String): List<RateItem> {
        return rateItemMapper.map(base)
    }

    override suspend fun getRatesCoroutine(base: String): List<RateItem> {
        // почему я тут могу сделать что-либо с функцией suspend без Global Scope например 
        val response = getLatestApiResultCoroutine(base = base)
        val favorites =
            ConverterApplication.PreferencesManager.get<List<CurrencyItem>>(
                ConverterApplication.PreferencesManager.SELECT_KEY
            )
                ?.filter { it.isFavorite }.orEmpty()
        val favoriteCurrencies = response?.filter { item ->
            favorites.find { favorite -> item.referenceCurrency.name == favorite.id } != null
        }.orEmpty()
        return favoriteCurrencies.ifEmpty { response }
    }

    override suspend fun getCurrentRatesCoroutine(
        base: String,
        referenceCurrencyCode: String
    ): Double {
        val response = getLatestApiResultCoroutine(base = base)
        val findItem = response?.find { it.referenceCurrency.name == referenceCurrencyCode }
        return findItem?.referenceCurrency?.value ?: 0.0
    }
}
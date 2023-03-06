package com.currency.converter.features.calculator.domain

import com.currency.converter.base.currency.CurrencyRatesRepository
import javax.inject.Inject

class UseCaseGetCurrentRates @Inject constructor(private val currencyRatesRepository: CurrencyRatesRepository) {
    suspend fun getCurrentRate(
        base: String,
        referenceCurrencyCode: String
    ): Double {
        val response = currencyRatesRepository.getLatestApiResult(base = base)
        val findItem = response?.find { it.referenceCurrency.name == referenceCurrencyCode }
        return findItem?.referenceCurrency?.value ?: 0.0
    }
}
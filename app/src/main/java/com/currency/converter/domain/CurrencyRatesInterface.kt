package com.currency.converter.domain

import com.currency.converter.features.rate.RateItem

interface CurrencyRatesInterface {
    suspend fun getLatestApiResultCoroutine(base: String): List<RateItem>
    suspend fun getRatesCoroutine(base: String): List<RateItem>
    suspend fun getCurrentRatesCoroutine(base: String, referenceCurrencyCode: String): Double
}
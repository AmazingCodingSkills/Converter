package com.converter.core.data.currencyrate

import com.converter.core.data.currencymodel.CurrencyItem


interface CurrencyRatesRepository {

    suspend fun getLatestApiResult(base: String): List<RateItem>

   //suspend fun  getAllValueApiResult(): List<CurrencyItem>?

}
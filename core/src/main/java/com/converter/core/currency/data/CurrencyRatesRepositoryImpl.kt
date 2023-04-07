package com.converter.core.currency.data


import com.converter.core.PreferencesManager
import com.converter.core.Constants.ALL_CURRENCY_KEY
import com.converter.core.currency.domain.RateItem
import com.converter.core.currency.domain.CurrencyRatesRepository
import com.converter.core.currency.CurrencyItemMapper
import com.converter.core.currency.RateItemMapper
import javax.inject.Inject


class CurrencyRatesRepositoryImpl @Inject constructor(private val currencyService: CurrencyService) :
    CurrencyRatesRepository {

    override suspend fun getLatestApiResult(base: String): List<RateItem> {
        val response = currencyService.getRates(base = base).ratesResponse
        return RateItemMapper.map(response)
    }

    override suspend fun loadAllValueCurrency() {
        val response = currencyService.getNameCountryCurrency().response
        val result = CurrencyItemMapper.map(response)
        PreferencesManager.put(result,ALL_CURRENCY_KEY)
    }
}
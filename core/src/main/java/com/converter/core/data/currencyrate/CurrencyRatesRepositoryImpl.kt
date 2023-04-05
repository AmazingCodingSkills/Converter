package com.converter.core.data.currencyrate


import com.converter.core.ConverterApplication
import com.converter.core.PreferencesManager
import com.converter.core.data.Constants
import com.converter.core.data.CurrencyService
import com.converter.core.data.currencymodel.CurrencyItem
import com.converter.core.data.room.Favorite
import com.converter.core.domain.CurrencyItemMapper
import com.converter.core.domain.RateItemMapper
import retrofit2.awaitResponse
import javax.inject.Inject


class CurrencyRatesRepositoryImpl @Inject constructor(private val currencyService: CurrencyService) :
    CurrencyRatesRepository {

    override suspend fun getLatestApiResult(base: String): List<RateItem> {
        val response = currencyService.getRates(base = base).ratesResponse
        return RateItemMapper.map(response)
    }

   /* override suspend fun getAllValueApiResult(): List<CurrencyItem>? {
        val response = currencyService.getNameCountryCurrency().awaitResponse()
        return if (response.isSuccessful) {
            val response = response.body()?.response
            response?.fiats?.map {
                CurrencyItem(
                    id = it.value.currency_code,
                    currencyName = it.value.currency_name,
                    isFavorite = false
                )
            }?.also {
                PreferencesManager.put(it, Constants.ALL_CURRENCY_KEY)
            }
        } else {
            null
        }
    }*/
/*
    override fun newMethod() {
        currencyService.getRates().map {

        }
        s
    }*/
}
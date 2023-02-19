package com.currency.converter.base

import com.currency.converter.ConverterApplication
import com.currency.converter.domain.CurrencyRatesInterface
import com.currency.converter.features.favorite.CurrencyItem
import com.currency.converter.features.rate.Currency
import com.currency.converter.features.rate.RateItem
import com.currency.converter.features.rate.RatesMetaResponse
import com.currency.converter.features.rate.countryname.CountryModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrencyRatesRepositoryImpl() : CurrencyRatesInterface {

    override suspend fun getLatestApiResultCoroutine(base: String): List<RateItem> {
        val response = RetrofitProvider.api.getRates(base = base).ratesResponse
        return response.let {
            it.rates.map { entry ->
                RateItem(
                    date = response.date,
                    referenceCurrency = Currency(
                        name = entry.key,
                        value = entry.value
                    ),
                    baseCurrencyName = response.base
                )

            }
        }
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

        val items = favoriteCurrencies.ifEmpty { response }
        return items
    }


    override suspend fun getCurrentRatesCoroutine(
        base: String,
        referenceCurrencyCode: String
    ): Double {
        val response = getLatestApiResultCoroutine(base = base)
        val findItem = response?.find { it.referenceCurrency.name == referenceCurrencyCode }
        val findValue = findItem?.referenceCurrency?.value ?: 0.0
        return findValue
    }


    fun getLatestApiResult(
        base: String,
        onFailure: (t: Throwable?) -> Unit,
        onSuccess: (List<RateItem>?) -> Unit
    ) {
        RetrofitProvider.api.getLatestValueCurrency(base = base)
            .enqueue(object : Callback<RatesMetaResponse> {

                override fun onFailure(call: Call<RatesMetaResponse>, t: Throwable) {
                    onFailure(t)
                }

                override fun onResponse(
                    call: Call<RatesMetaResponse>,
                    response: Response<RatesMetaResponse>
                ) {
                    val isFailure = response.code() in 400..401
                    if (isFailure) onFailure(null)

                    val response = response.body()?.ratesResponse
                    val rateItems = response?.let {
                        it.rates.map { entry -> // ??
                            RateItem(
                                date = response.date,
                                referenceCurrency = Currency(
                                    name = entry.key,
                                    value = entry.value
                                ),
                                baseCurrencyName = response.base
                            )

                        }
                    }
                    onSuccess(rateItems)

                }
            })
    }

    fun getRates(
        base: String,
        onFailure: (t: Throwable?) -> Unit,
        onSuccess: (List<RateItem>?) -> Unit
    ) {
        getLatestApiResult(base = base, onFailure = onFailure) { latestRates ->
            val favorites =
                ConverterApplication.PreferencesManager.get<List<CurrencyItem>>(
                    ConverterApplication.PreferencesManager.SELECT_KEY
                )
                    ?.filter { it.isFavorite }.orEmpty()

            val favoriteCurrencies = latestRates?.filter { item ->
                favorites.find { favorite -> item.referenceCurrency.name == favorite.id } != null
            }.orEmpty()

            val items = favoriteCurrencies.ifEmpty { latestRates }
            onSuccess(items)

        }
    }

    fun getCurrentRates(
        baseCurrencyCode: String,
        referenceCurrencyCode: String,
        onResult: (Double) -> Unit
    ) {
        getLatestApiResult(base = baseCurrencyCode, {}) { rates ->
            val findItem = rates?.find { it.referenceCurrency.name == referenceCurrencyCode }
            val findValue = findItem?.referenceCurrency?.value ?: 0.0
            onResult(findValue)
        }
    }

    val selectedCurrency = ConverterApplication.PreferencesManager.get<CountryModel>(
        ConverterApplication.PreferencesManager.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
    )
}
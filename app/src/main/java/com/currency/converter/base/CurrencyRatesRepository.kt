package com.currency.converter.base

import com.currency.converter.ConverterApplication
import com.currency.converter.features.favorite.CurrencyItem
import com.currency.converter.features.rate.Currency
import com.currency.converter.features.rate.RateItem
import com.currency.converter.features.rate.RatesMetaResponse
import com.currency.converter.features.rate.countryname.CountryModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CurrencyRatesRepository {

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
        getLatestApiResult(base = baseCurrencyCode,{}) { rates ->
            val findItem = rates?.find { it.referenceCurrency.name == referenceCurrencyCode }
            val findValue = findItem?.referenceCurrency?.value ?: 0.0
            onResult(findValue)
        }
    }

    val selectedCountries = ConverterApplication.PreferencesManager.get<CountryModel>(
        ConverterApplication.PreferencesManager.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
    )
}

const val MESSAGE_EXCEPTION_FOR_CALCULATOR = "Введите число, которое не начиначется с  '.'"
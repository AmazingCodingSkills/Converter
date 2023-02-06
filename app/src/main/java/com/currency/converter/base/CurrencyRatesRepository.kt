package com.currency.converter.base

import com.currency.converter.features.rate.Currency
import com.currency.converter.features.rate.RateItem
import com.currency.converter.features.rate.RatesMetaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CurrencyRatesRepository {

    fun getLatestApiResult(base : String, onSuccess: (List<RateItem>?) -> Unit) {
        RetrofitProvider.api.getLatestValueCurrency(base = base)
            .enqueue(object : Callback<RatesMetaResponse> {
                override fun onFailure(call: Call<RatesMetaResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<RatesMetaResponse>,
                    response: Response<RatesMetaResponse>
                ) {
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
}
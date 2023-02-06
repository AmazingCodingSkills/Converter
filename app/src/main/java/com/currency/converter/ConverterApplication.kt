package com.currency.converter

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.currency.converter.ConverterApplication.PreferencesManager.ALL_CURRENCY_KEY
import com.currency.converter.ConverterApplication.PreferencesManager.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
import com.currency.converter.base.RetrofitProvider
import com.currency.converter.features.favorite.CountryService
import com.currency.converter.features.favorite.CurrencyItem
import com.currency.converter.features.favorite.MetaCurrenciesResponse
import com.currency.converter.features.rate.countryname.CountryModel
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConverterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("APP", "I'ts fine")
        getAllInformationListApplication()
        PreferencesManager.with(this)
        val firstLaunchPref =
            PreferencesManager.get<CountryModel>(BASE_CURRENCIES_FOR_VARIOUS_COUNTRY)
        if (firstLaunchPref == null) { // с помощью этой проверки префов могу установить значение при первом запуске
            PreferencesManager.put(
                CountryService.countryList().first(),
                BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
            )
        }
    }

    private fun getAllInformationListApplication() {
        RetrofitProvider.api.getNameCountryCurrency()
            .enqueue(object : Callback<MetaCurrenciesResponse> {
                override fun onFailure(call: Call<MetaCurrenciesResponse>, t: Throwable) {
                    Log.d("commontag", "${t}")
                }

                override fun onResponse(
                    call: Call<MetaCurrenciesResponse>,
                    response: Response<MetaCurrenciesResponse>
                ) {
                    Log.d("responsetat", "OK 3")
                    val response = response.body()?.response
                    val itemModels: List<CurrencyItem>? = response?.let {
                        it.fiats.map {
                            CurrencyItem(
                                id = it.value.currency_code,
                                currencyName = it.value.currency_name,
                                isFavorite = false
                            )
                        }
                    }
                    PreferencesManager.put(itemModels, ALL_CURRENCY_KEY)
                }
            })
    }

    object PreferencesManager {
        lateinit var sp: SharedPreferences
        private const val PREFERENCES_FILE_NAME = "PREFERENCES_FILE_NAME"
        const val SELECT_KEY = "favorite_currencies_key"
        const val ALL_CURRENCY_KEY = "all_currencies_key"
        const val FAVORITE_CURRENCIES_KEY = "only_selected_currencies"
        const val BASE_CURRENCIES_FOR_VARIOUS_COUNTRY = "base_currency"
        const val SELECT_CURRENCY_FROM_CONVERT = "from_currency_convert"
        const val SELECT_CURRENCY_TO_CONVERT = "to_currency_convert"
        fun with(application: Application) {
            sp = application.getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE)
        }

        fun <T> put(`object`: T, key: String) {
            val jsonString = GsonBuilder().create().toJson(`object`)
            sp.edit().putString(key, jsonString).apply()
        }

        inline fun <reified T> get(key: String): T? {
            val value = sp.getString(key, null)
            return GsonBuilder().create().fromJson(value, object : TypeToken<T>() {}.type)
        }

    }

}

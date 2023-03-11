package com.currency.converter

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.currency.converter.ConverterApplication.PreferencesManager.ALL_CURRENCY_KEY
import com.currency.converter.ConverterApplication.PreferencesManager.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
import com.currency.converter.base.favoritemodel.CurrencyItem
import com.currency.converter.base.favoritemodel.MetaCurrenciesResponse
import com.currency.converter.base.room.Favorite
import com.currency.converter.features.rate.countryname.CountryModel
import com.currency.converter.features.rate.data.CountryService
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class ConverterApplication @Inject constructor() : Application() {


    val appComponentCreate: AppComponent by lazy {
        DaggerAppComponent.factory().create(application)
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        appComponent = appComponentCreate
        getAllCountryCurrency()
        PreferencesManager.with(this)
        val firstLaunchPref =
            PreferencesManager.get<CountryModel>(BASE_CURRENCIES_FOR_VARIOUS_COUNTRY)
        if (firstLaunchPref == null) { // с помощью этой проверки префов могу установить значение при первом запуске
            PreferencesManager.put(
                CountryService.countryList().first(),
                BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
            )
        }
        val firstLaunchDB = PreferencesManager.get<List<CurrencyItem>>(ALL_CURRENCY_KEY)
        GlobalScope.launch(Dispatchers.IO) {
            if (appComponent.providesRoom().getAll().isNullOrEmpty()) {
                if (firstLaunchDB != null) {
                    appComponent.providesRoom().insertAll(firstLaunchDB.map { Favorite(it.id,it.currencyName,it.isFavorite) })
                }
            }
        }
    }

    private fun getAllCountryCurrency() {
        val currencyService = appComponentCreate.providesCurrencyService()
        currencyService.getNameCountryCurrency()
            .enqueue(object : Callback<MetaCurrenciesResponse> {
                override fun onFailure(call: Call<MetaCurrenciesResponse>, t: Throwable) {
                    Log.d("commontag", "${t}")
                }

                override fun onResponse(
                    call: Call<MetaCurrenciesResponse>,
                    response: Response<MetaCurrenciesResponse>
                ) {
                    val response = response.body()?.response
                    val itemModels: List<CurrencyItem>? = response?.let { responseCurrencies ->
                        responseCurrencies.fiats.map {
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
        const val SELECT_KEY = "FAVORITE_CURRENCIES_KEY"
        const val ALL_CURRENCY_KEY = "ALL_CURRENCIES_KEY"
        const val FAVORITE_CURRENCIES_KEY = "ONLY_SELECTED_CURRENCIES"
        const val BASE_CURRENCIES_FOR_VARIOUS_COUNTRY = "BASE_CURRENCY"
        const val MY_REQUEST_KEY = "my_request_key"


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

    companion object {
        lateinit var application: Application
            private set
        lateinit var appComponent: AppComponent
            private set
    }
}

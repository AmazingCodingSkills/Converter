package com.converter.core

import android.app.Application
import android.util.Log
import com.converter.core.data.Constants.ALL_CURRENCY_KEY
import com.converter.core.data.Constants.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
import com.converter.core.data.currencymodel.CurrencyItem
import com.converter.core.data.BaseCountryService
import com.converter.core.data.currencymodel.MetaCurrenciesResponse
import com.converter.core.di.AppComponent
import com.converter.core.di.DaggerAppComponent
import com.converter.core.data.room.Favorite
import com.converter.core.data.favouritecurrencymodel.CountryModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import javax.inject.Inject


class ConverterApplication @Inject constructor() : Application() {

    private val myScope = CoroutineScope(Job() + Dispatchers.Default)

    val appComponentCreate: AppComponent by lazy {
        DaggerAppComponent.factory().create(application)
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        appComponent = appComponentCreate
        PreferencesManager.with(this)
        firstSetValuePrefs()
        getAllCountryCurrency()
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
                    firstSetValueDB()
                }
            })
    }

    /*private suspend fun getAllCountryCurrency(): List<CurrencyItem>? {
        val currencyService = appComponentCreate.providesCurrencyService()
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
                PreferencesManager.put(it, ALL_CURRENCY_KEY)
                firstSetValueDB()
            }

        } else {
            null
        }

    }*/

    private fun firstSetValuePrefs() {
        val firstLaunchPref =
            PreferencesManager.get<CountryModel>(BASE_CURRENCIES_FOR_VARIOUS_COUNTRY)
        if (firstLaunchPref == null) {
            PreferencesManager.put(
                BaseCountryService.countryList().first(),
                BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
            )
        }
    }

    private  fun firstSetValueDB() {
        myScope.launch {
            withContext(Dispatchers.IO) {
                if (appComponent.providesRoom().getAll().isEmpty()) {
                    val itemModels = PreferencesManager.get<List<CurrencyItem>>(ALL_CURRENCY_KEY)
                    if (itemModels != null && itemModels.isNotEmpty()) {
                        appComponent.providesRoom().insertAll(itemModels.map {
                            Favorite(
                                it.id,
                                it.currencyName,
                                it.isFavorite
                            )
                        })
                    }
                }
            }
        }
    }


    companion object {
        lateinit var application: Application
            private set
        lateinit var appComponent: AppComponent
            private set
    }
}


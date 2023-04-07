package com.converter.core

import android.app.Application
import android.util.Log
import com.converter.core.di.AppComponent
import com.converter.core.di.DaggerAppComponent
import com.converter.core.country.data.BaseCountryService
import com.converter.core.Constants.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
import com.converter.core.country.data.CountryModel
import kotlinx.coroutines.*
import javax.inject.Inject


class ConverterApplication @Inject constructor() : Application() {

    private val myScope = CoroutineScope(Job() + Dispatchers.Default)

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(application)
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        PreferencesManager.with(this)
        firstSetValuePrefs()
        getAllCountryCurrency()
    }

    private fun getAllCountryCurrency() {
        myScope.launch {
            try {
                appComponent.currencyRatesRepository().loadAllValueCurrency()
                withContext(Dispatchers.IO) {
                    appComponent.provideFavouriteCurrencyRepository().loadRatesInInitDB()
                }
            } catch (e: Throwable) {
                Log.e("errorDB", "$e")
            }
        }
    }

    private fun firstSetValuePrefs() {
        val firstLaunchPref =
            PreferencesManager.get<CountryModel>(BASE_CURRENCIES_FOR_VARIOUS_COUNTRY)
        if (firstLaunchPref == null) {
            PreferencesManager.put(
                BaseCountryService.countryList(baseContext).first(),
                BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
            )
        }
    }

    companion object {
        lateinit var application: Application
            private set
    }
}


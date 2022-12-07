package com.currency.converter

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.currency.converter.ConverterApplication.ModelPreferencesManager.ALL_LIST_KEY
import com.currency.converter.base.RetrofitProvider
import com.currency.converter.features.favorite.CurrencyItem
import com.currency.converter.features.favorite.MetaCurrenciesResponse
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


interface Programmer {
    fun writeCode()
}

class Senior : Programmer {
    override fun writeCode() {
        println("OK!")
    }

    fun test() {

    }
}

class Junior : Programmer {

    override fun writeCode() {
        throw RuntimeException("TEST")
    }

    fun test() {

    }
}

class Work {
    val senior = Senior()
    val junior = Junior()
    val programmers = listOf<Programmer>(senior, junior)

    fun doSomeStaff() {
        programmers.forEach { programmer ->
            programmer.writeCode()
        }
    }
}


class ConverterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("APP", "I'ts fine")
        getAllInformationListApplication()
        ModelPreferencesManager.with(this)
    }

    private fun getAllInformationListApplication() {
        RetrofitProvider.api.getInformationListX().enqueue(object : Callback<MetaCurrenciesResponse> {
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
                            currencyName = it.value.currency_name,
                            isFavorite = false
                        )
                    }
                }
                Log.d("responsetat", "${itemModels}")
                ModelPreferencesManager.put(itemModels, ALL_LIST_KEY)


            }
        })
    }

    object ModelPreferencesManager {
        lateinit var sp: SharedPreferences
        private const val PREFERENCES_FILE_NAME = "PREFERENCES_FILE_NAME"

        const val FAVORITE_KEY = "favorite_currencies_key"
        const val ALL_LIST_KEY = "all_currencies_key"

        fun with(application: Application) {
            sp = application.getSharedPreferences(PREFERENCES_FILE_NAME, MODE_PRIVATE)
        }

        fun <T> put(`object`: T, key: String) {
            val jsonString = GsonBuilder().create().toJson(`object`)
            sp.edit().putString(key, jsonString).apply()
        }

        /*fun <T> get(key: String): T? {
            val value = sp.getString(key, null)
            return GsonBuilder().create().fromJson(value, object : TypeToken<T>() {}.type)*/
        inline fun <reified T> get(key: String): T? {
            val value = sp.getString(key, null)
            return GsonBuilder().create().fromJson(value, object : TypeToken<T>() {}.type)
        }

    }

}

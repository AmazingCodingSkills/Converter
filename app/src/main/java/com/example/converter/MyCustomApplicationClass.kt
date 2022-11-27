package com.example.converter

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.example.converter.Common.Common
import com.example.converter.adaptersCurrencies.ItemModelX
import com.example.converter.adaptersCurrencies.TestResponseCurrencies
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCustomApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("APP", "I'ts fine")
        getAllInformationListApplication()
        ModelPreferencesManager.with(this)
    }

    private fun getAllInformationListApplication() {
        Common.api.getInformationListX().enqueue(object : Callback<TestResponseCurrencies> {
            override fun onFailure(call: Call<TestResponseCurrencies>, t: Throwable) {
                Log.d("commontag", "${t}")
            }

            override fun onResponse(
                call: Call<TestResponseCurrencies>,
                response: Response<TestResponseCurrencies>
            ) {
                Log.d("responsetat", "OK 3")
                val response = response.body()?.response
                val itemModels: List<ItemModelX>? = response?.let {
                    it.fiats.map {
                        ItemModelX(
                            currencyName = it.value.currency_name,
                            isFavorite = it.value.favorite
                        )
                    }
                }
                Log.d("responsetat", "${itemModels}")
                ModelPreferencesManager.put(itemModels, "KEY_ONE")
            }
        })
    }

    object ModelPreferencesManager {
        lateinit var sp: SharedPreferences
        private const val PREFERENCES_FILE_NAME = "PREFERENCES_FILE_NAME"

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
        inline fun <reified T> get(key: String): T? { // Вот про это спросить + опять проблема Tree Map если верхняя запись
            val value = sp.getString(key, null)
            return GsonBuilder().create().fromJson(value, object : TypeToken<T>() {}.type)
        }

    }

}

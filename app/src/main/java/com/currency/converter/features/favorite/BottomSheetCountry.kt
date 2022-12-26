package com.example.converter.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.ConverterApplication.PreferencesManager.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
import com.currency.converter.features.rate.countryname.CountryAdapter
import com.currency.converter.features.rate.countryname.CountryItem
import com.currency.converter.features.rate.countryname.CountryModel
import com.example.converter.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


// LiveData
// Rx -> Subject
// coroutines -> flow (shared flow)
// own desicion

class BottomSheetCountry : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding

    private lateinit var countryAdapter: CountryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCountry()
    }

    private fun initCountry() {
        binding.apply {
            recyclerFavorite.layoutManager = LinearLayoutManager(activity)
            countryAdapter = CountryAdapter { item ->
                saveItem(item.countryModel)
                Log.d("Save", "{$item}")
               dismiss()
            }
            recyclerFavorite.adapter = countryAdapter    // up: не хватало инициализации переменной countryAdapter ошибка kotlin.UninitializedPropertyAccessException: lateinit property countryAdapter has not been initialized


            // get selected country from sp
            // generate new list with selected country
            val onlySelectCountry = ConverterApplication.PreferencesManager.get<CountryModel>(
                BASE_CURRENCIES_FOR_VARIOUS_COUNTRY)
            val countries = countryList()
            // counrtyModel + list<counrtModel> =  list<CounryItem>
            // countryAdapter.countryNames = items сеттер
            countryAdapter.addAll(items)
                  // при подобной записи, на том, где остановились 16.12 crush

        }
    }

    private fun saveItem(item: CountryModel) {
       val saveSelectCountry = ConverterApplication.PreferencesManager.put(
            item,
            BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
        ) // пытаюсь таким образом сохранить выбор, но есть устойчевое чувств, что чего-то не хватает
       Log.d("qwerty", "${saveSelectCountry}")
    }

    fun countryList(): List<CountryModel> {
        val listOfCountries = listOf(
            CountryModel("Россия", "RUB"),
            CountryModel("Казахстан", "KZT"),
            CountryModel("Азербайджан", "AZN"),
            CountryModel("Армения", "AMD"),
            CountryModel("Грузия", "GEL"),
            CountryModel("Узбекистан", "UZS"),
            CountryModel("Кыргызстан", "KGS"),
            CountryModel("Беларусь", "BYN"),
            CountryModel("Таджикистан", "TJS"),
            CountryModel("Молдова", "MDL"),
            CountryModel("Туркменистан", "TMT"),
            CountryModel("Украина", "UAH")
        )
        return listOfCountries
    }
}






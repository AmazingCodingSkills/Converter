package com.example.converter.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.ConverterApplication.PreferencesManager.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
import com.currency.converter.base.EventBus.subject
import com.currency.converter.features.rate.countryname.CountryAdapter
import com.currency.converter.features.rate.countryname.CountryItem
import com.currency.converter.features.rate.countryname.CountryModel
import com.example.converter.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
                subject.update(item.countryModel)
                dismiss()
            }
            recyclerFavorite.adapter = countryAdapter
            val selectCountryBottomSheet =
                ConverterApplication.PreferencesManager.get<CountryModel>(
                    BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
                )
            val countries = countryList()
            val listCountryItem: List<CountryItem> = countries.map {
                CountryItem(
                    countryModel = it,
                    selected = selectCountryBottomSheet == it
                )
            }

            countryAdapter.addAll(listCountryItem)
        }
    }

    private fun saveItem(item: CountryModel) {
        val saveSelectCountry = ConverterApplication.PreferencesManager.put(
            item,
            BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
        )
        Log.d("qwerty", "${saveSelectCountry}")
    }

    private fun countryList(): List<CountryModel> {
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






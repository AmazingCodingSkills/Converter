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
import com.currency.converter.features.favorite.CountryService.countryList
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
            recyclerBottomSheetFromFirstScreen.layoutManager = LinearLayoutManager(activity)
            countryAdapter = CountryAdapter { item ->
                saveItem(item.countryModel)
                Log.d("Black5", "${item.countryModel}")
                subject.update(item.countryModel)
                dismiss()
            }
            recyclerBottomSheetFromFirstScreen.adapter = countryAdapter
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
        ConverterApplication.PreferencesManager.put(
            item,
            BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
        )
    }
}






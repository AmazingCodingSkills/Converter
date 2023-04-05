package com.example.rate.presentation.basecurrency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.converter.core.data.Constants.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
import com.converter.core.data.Constants.MY_REQUEST_KEY
import com.converter.core.PreferencesManager
import com.converter.core.data.favouritecurrencymodel.CountryItem
import com.converter.core.data.favouritecurrencymodel.CountryModel
import com.converter.core.data.BaseCountryService.countryList
import com.example.rate.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BaseCurrency : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var baseCurrencyAdapter: BaseCurrencyAdapter

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
            baseCurrencyAdapter = BaseCurrencyAdapter { item ->
                saveItem(item.countryModel)
                val result = Bundle().apply {
                    putSerializable("data", item.countryModel)
                }
                parentFragmentManager.setFragmentResult(MY_REQUEST_KEY,result)
                dismiss()
            }
            recyclerBottomSheetFromFirstScreen.adapter = baseCurrencyAdapter
            val selectCountryBottomSheet =
                PreferencesManager.get<CountryModel>(
                    BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
                )
            val countries = countryList()
            val listCountryItem: List<CountryItem> = countries.map {
                CountryItem(
                    countryModel = it,
                    selected = selectCountryBottomSheet == it
                )
            }

            baseCurrencyAdapter.addAll(listCountryItem)
        }
    }

    private fun saveItem(item: CountryModel) {
        PreferencesManager.put(
            item,
            BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
        )
    }
}






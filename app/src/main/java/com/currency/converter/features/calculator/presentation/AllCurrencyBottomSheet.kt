package com.currency.converter.features.calculator.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.ConverterApplication.PreferencesManager.ALL_CURRENCY_KEY
import com.currency.converter.ConverterApplication.PreferencesManager.SELECT_CURRENCY_FROM_CONVERT
import com.currency.converter.base.favoritemodel.CurrencyItem
import com.example.converter.databinding.FragmentAllCurrencyBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AllCurrencyBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAllCurrencyBottomSheetBinding
    private lateinit var calculatorAdapter: CalculatorAdapter
    val allCountryList =
        ConverterApplication.PreferencesManager.get<List<CurrencyItem>>(ALL_CURRENCY_KEY)
            .orEmpty()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllCurrencyBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchRV()
    }

    fun launchRV() {
        binding.apply {
            recyclerAllCurrencies.layoutManager = LinearLayoutManager(activity)
            calculatorAdapter = CalculatorAdapter() { item ->
                saveFromConvert(item)
                requireActivity().supportFragmentManager.setFragmentResult(
                    "CURRENCY_KEY", bundleOf(
                        "SELECTED_CURRENCY" to item,
                        "TAG" to tag
                    )
                )
                dismiss()
            }

            recyclerAllCurrencies.adapter = calculatorAdapter

            calculatorAdapter.addAll(allCountryList)


            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterSearchList(newText)
                    return true
                }
            })

        }
    }


    private fun filterSearchList(query: String?) {
        if (query != null) {
            val filteredList = allCountryList.filter { it.currencyName.contains(query) }
            if (filteredList.isEmpty()) {
                Toast.makeText(requireActivity(), "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                calculatorAdapter.setFilteredList(filteredList)
            }
        }
    }


    private fun saveFromConvert(item: CurrencyItem) {
        val fromConvertSharedPreference = ConverterApplication.PreferencesManager.put(
            item,
            SELECT_CURRENCY_FROM_CONVERT
        )
        Log.d("checkShered", "${fromConvertSharedPreference}")
    }
}
package com.currency.converter.features.calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.ConverterApplication.PreferencesManager.ALL_CURRENCY_KEY
import com.currency.converter.ConverterApplication.PreferencesManager.SELECT_CURRENCY_FROM_CONVERT
import com.currency.converter.features.favorite.CurrencyItem
import com.example.converter.databinding.FragmentAllCurrencyBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AllCurrencyBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAllCurrencyBottomSheetBinding
    private lateinit var calculatorAdapter: CalculatorAdapter


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
                requireActivity().supportFragmentManager.setFragmentResult("CURRENCY_KEY", bundleOf(
                    "SELECTED_CURRENCY" to item,
                    "TAG" to tag
                ))
                dismiss()
            }

            recyclerAllCurrencies.adapter = calculatorAdapter

            val allCountryList =
                ConverterApplication.PreferencesManager.get<List<CurrencyItem>>(ALL_CURRENCY_KEY)
                    .orEmpty()
            calculatorAdapter.addAll(allCountryList)

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
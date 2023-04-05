package com.example.calculator.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.converter.core.data.Constants.ALL_CURRENCY_KEY
import com.converter.core.PreferencesManager
import com.converter.core.data.currencymodel.CurrencyItem
import com.converter.core.presentation.hideKeyboard
import com.converter.core.presentation.showKeyboard
import com.example.calculator.databinding.FragmentAllCurrencyBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal class AllCurrencyBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAllCurrencyBottomSheetBinding
    private lateinit var calculatorAdapter: CalculatorAdapter
    private val allCountryList =
        PreferencesManager.get<List<CurrencyItem>>(ALL_CURRENCY_KEY)
            .orEmpty()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllCurrencyBottomSheetBinding.inflate(inflater, container, false)
        binding.recyclerAllCurrencies.visibility = View.VISIBLE
        binding.hintEmptyScreenConverter.visibility = View.GONE
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding.searchView.requestFocus()
        binding.searchView.showKeyboard()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchRV()
    }

    private fun launchRV() {
        binding.apply {
            recyclerAllCurrencies.layoutManager = LinearLayoutManager(activity)
            calculatorAdapter = CalculatorAdapter() { item ->

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

            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
            binding.recyclerAllCurrencies.visibility = View.VISIBLE
            binding.hintEmptyScreenConverter.visibility = View.GONE
            val filteredList = allCountryList.filter {
                it.currencyName.contains(query)
            }
            if (filteredList.isEmpty()) {
                binding.recyclerAllCurrencies.visibility = View.GONE
                binding.hintEmptyScreenConverter.visibility = View.VISIBLE
                binding.searchView.visibility = View.VISIBLE
                Toast.makeText(requireActivity(), "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                calculatorAdapter.setFilteredList(filteredList)
            }
        }
    }

    override fun onDestroy() {
        binding.searchView.hideKeyboard()
        super.onDestroy()

    }
}
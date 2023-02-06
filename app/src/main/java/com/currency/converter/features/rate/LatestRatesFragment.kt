package com.currency.converter.features.rate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.ConverterApplication.PreferencesManager.BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
import com.currency.converter.base.CurrencyRatesRepository
import com.currency.converter.base.EventBus.subject
import com.currency.converter.base.Observer
import com.currency.converter.features.favorite.CurrencyItem
import com.currency.converter.features.favorite.MainFavoriteFragment
import com.currency.converter.features.rate.countryname.CountryModel
import com.example.converter.R
import com.example.converter.databinding.FragmentLatestValueBinding
import com.example.converter.fragment.BottomSheetCountry


class LatestRatesFragment : Fragment() {

    private lateinit var binding: FragmentLatestValueBinding
    private lateinit var latestRatesAdapter: LatestRatesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLatestValueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        binding.changeCurrencyButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.bottom_navigation_replacement, MainFavoriteFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.bottomSheetButton.setOnClickListener {
            val bottomSheet = BottomSheetCountry()
            bottomSheet.show(childFragmentManager, "TAG")
        }
    }

    private fun initRcView() = with(binding) {
        recyclerLatest.layoutManager = LinearLayoutManager(activity)
        latestRatesAdapter = LatestRatesAdapter()
        recyclerLatest.adapter = latestRatesAdapter
        subject.addObserver(object : Observer<CountryModel?> {

            override fun update(value: CountryModel?) {
                if (value != null) {
                    getLatestValueForSelectCurrency(value.baseCurrency, value.icon)
                }
            }
        })

        val selectedCountries = ConverterApplication.PreferencesManager.get<CountryModel>(
            BASE_CURRENCIES_FOR_VARIOUS_COUNTRY
        )
        Log.d("Black","$selectedCountries")

        if (selectedCountries != null) {
            getLatestValueForSelectCurrency(selectedCountries.baseCurrency, selectedCountries.icon)
        }
    }


    private fun getLatestValueForSelectCurrency(base: String, icon: Int) {
        binding.bottomSheetButton.setImageResource(icon)
        CurrencyRatesRepository.getLatestApiResult(base = base) { latestRates ->
            val favorites =
                ConverterApplication.PreferencesManager.get<List<CurrencyItem>>(ConverterApplication.PreferencesManager.SELECT_KEY)
                    ?.filter { it.isFavorite }.orEmpty()
            Log.d("Black1", "$favorites")
            val favoriteCurrencies = latestRates?.filter { item ->
                favorites.find { favorite -> item.referenceCurrency.name == favorite.id } != null
            }.orEmpty()
            Log.d("Black2", "$favoriteCurrencies")
            val items = favoriteCurrencies.ifEmpty { latestRates }
            Log.d("Black3", "$items")
            latestRatesAdapter.submitList(items)
        }
    }
    companion object {
        fun newInstance() = LatestRatesFragment()
    }
}

package com.currency.converter.features.rate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.ConverterApplication.PreferencesManager.SELECT_KEY
import com.currency.converter.base.RetrofitProvider
import com.currency.converter.features.favorite.CurrencyItem
import com.currency.converter.features.favorite.MainFavoriteFragment
import com.example.converter.R
import com.example.converter.databinding.FragmentLatestValueBinding
import com.example.converter.fragment.BottomSheet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            binding.changeCurrencyButton.setTextColor(resources.getColor(android.R.color.black))
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.bottom_navigation_replacement, MainFavoriteFragment())
                addToBackStack(null)
                commit()
                // (1) -> (2)
            }
        }
        binding.bottomSheetButton.setOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(childFragmentManager, "TAG")


        }
    }


    private fun initRcView() = with(binding) {
        recyclerLatest.layoutManager = LinearLayoutManager(activity)
        latestRatesAdapter = LatestRatesAdapter()
        recyclerLatest.adapter = latestRatesAdapter

        getLatestValueForSelectCurrency()
    }


    private fun getLatestValueForSelectCurrency() {
       /* val favorites =
            ConverterApplication.PreferencesManager.get<List<CurrencyItem>>(FAVORITE_CURRENCIES_KEY)
                ?.map { it.id }?.takeIf { it.isEmpty().not() }*/
        RetrofitProvider.api.getLatestValueCurrency()
            .enqueue(object : Callback<RatesMetaResponse> {
                override fun onFailure(call: Call<RatesMetaResponse>, t: Throwable) {
                    Log.d("commontag", "${t}")
                }


                override fun onResponse(
                    call: Call<RatesMetaResponse>,
                    response: Response<RatesMetaResponse>
                ) {

                    Log.d("responsetag", "OK 2")

                    val response = response.body()?.ratesResponse
                    val rateItems = response?.let {
                        it.rates.map { entry -> // ??
                            RateItem(
                                date = response.date,
                                referenceCurrency = Currency(
                                    name = entry.key,
                                    value = entry.value
                                ),
                                baseCurrencyName = response.base
                            )
                        }
                    }
                    val favorites = ConverterApplication.PreferencesManager.get<List<CurrencyItem>>(SELECT_KEY)
                         ?.filter { it.isFavorite }.orEmpty()
                     val favoriteCurrencies = rateItems?.filter { item ->
                         favorites.find { favorite -> item.referenceCurrency.name == favorite.id } != null
                     }.orEmpty()
                     val items = favoriteCurrencies.ifEmpty { rateItems }
                    latestRatesAdapter.submitList(items)
                    Log.d("responsetag", "${rateItems}")

                }
            })
    }

    companion object {
        fun newInstance() = LatestRatesFragment()
    }

}
//Вариант кода с логикой внутри, не используя бэк
/*                val favorites = ConverterApplication.ModelPreferencesManager.get<List<CurrencyItem>>(FAVORITE_KEY)
                    ?.filter { it.isFavorite }.orEmpty()
                val favoriteCurrencies = rateItems?.filter { item ->
                   favorites.find { favorite -> item.referenceCurrency.name == favorite.id } != null
                }.orEmpty()
                val items = favoriteCurrencies.ifEmpty { rateItems }*/

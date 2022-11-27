package com.currency.converter.features.rate

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.base.RetrofitProvider
import com.currency.converter.features.favorite.MainFavoriteFragment
import com.example.converter.R
import com.example.converter.adaptersLatest.ConvertAdapter
import com.example.converter.adaptersLatest.ItemModel
import com.example.converter.adaptersLatest.TestResponse
import com.example.converter.adaptersLatest.ValueCurrency
import com.example.converter.databinding.FragmentLatestValueBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LatestRatesFragment : Fragment() {


    private lateinit var binding: FragmentLatestValueBinding
    private lateinit var adapter: LatestRatesAdapter
    lateinit var btnChangeCountries: Button


    private val preferences: SharedPreferences by lazy(LazyThreadSafetyMode.NONE) {
        requireActivity().applicationContext.getSharedPreferences(
            "SettingsPreferences",
            Context.MODE_PRIVATE
        )
    }

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

        binding.favouriteButton.setOnClickListener {
            
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, MainFavoriteFragment())
                addToBackStack(null) //??
                commit()
                // (1) -> (2)
            }
        }
        binding.firstScreenLogo.setOnClickListener{
                val bottomSheet = BottomSheet()
                    bottomSheet.show(childFragmentManager,"TAG")


            }
    }


    private fun initRcView() = with(binding) {
        recyclerLatest.layoutManager = LinearLayoutManager(activity)
        adapter = LatestRatesAdapter()
        recyclerLatest.adapter = adapter

        getAllInformationList()
    }


    private fun getAllInformationList() {
        RetrofitProvider.api.getInformationList().enqueue(object : Callback<RatesMetaResponse> {
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
                adapter.submitList(rateItems)
                Log.d("responsetag", "${rateItems}")

            }
        })
    }

    /*private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }*/

    companion object {
        fun newInstance() = LatestRatesFragment()
    }

}

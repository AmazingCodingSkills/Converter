package com.example.converter.fragment

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
import com.example.converter.Common.Common
import com.example.converter.R
import com.example.converter.adaptersLatest.ConvertAdapter
import com.example.converter.adaptersLatest.ItemModel
import com.example.converter.adaptersLatest.TestResponse
import com.example.converter.adaptersLatest.ValueCurrency
import com.example.converter.databinding.FragmentLatestValueBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LatestValueFragment : Fragment() {


    private lateinit var binding: FragmentLatestValueBinding
    private lateinit var adapter: ConvertAdapter
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
                replace(R.id.fl_wrapper, FavoritesFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.firstScreenLogo.setOnClickListener{
                val bottomSheet = BottomSheet()
                    bottomSheet.show(childFragmentManager,"TAG")


            }
    }


    private fun initRcView() = with(binding) {
        recyclerLatest.layoutManager = LinearLayoutManager(activity)
        adapter = ConvertAdapter()
        recyclerLatest.adapter = adapter

        getAllInformationList()
    }


    private fun getAllInformationList() {
        Common.api.getInformationList().enqueue(object : Callback<TestResponse> {
            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                Log.d("commontag", "${t}")
            }


            override fun onResponse(
                call: Call<TestResponse>,
                response: Response<TestResponse>
            ) {

                Log.d("responsetag", "OK 2")

                val response = response.body()?.response
                val itemModels = response?.let {
                    it.rates.map { entry ->
                        ItemModel(
                            date = response.date,
                            referenceCurrency = ValueCurrency(
                                name = entry.key,
                                value = entry.value
                            ),
                            baseCurrencyName = response.base
                        )
                    }
                }
                adapter.submitList(itemModels)
                Log.d("responsetag", "${itemModels}")

            }
        })
    }

    /*private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }*/


    companion object {
        fun newInstance() = LatestValueFragment()
    }

}

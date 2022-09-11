package com.example.converter.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converter.Common.Common
import com.example.converter.adaptersLatest.ConvertAdapter
import com.example.converter.adaptersLatest.ItemModel
import com.example.converter.adaptersLatest.TestResponse
import com.example.converter.adaptersLatest.ValueCurrency
import com.example.converter.databinding.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LatestValueFragment : Fragment() {


    private lateinit var binding: FragmentLatestValueBinding
    private lateinit var adapter: ConvertAdapter/*ОПА*/




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
                    listOf(
                        ItemModel(

                            date = it.date,
                            currency = ValueCurrency(currency = it.rates.toString() /*, value = it.rates.RUB*/),
                            base = it.base),
                        /*ItemModel(
                            date = it.date,
                            currency = ValueCurrency(currency = it.ratex.toString(), value = it.rates.KZT),
                            base = it.base),
                        ItemModel(
                            date = it.date,
                            currency = ValueCurrency(currency = "HKD", value = it.rates.HKD),
                            base = it.base)*/
                    )
                }
                adapter.submitList(itemModels)
                Log.d("responsetag", "${itemModels}")

            }
        })
    }



    companion object {
        fun newInstance() = LatestValueFragment()
    }
}
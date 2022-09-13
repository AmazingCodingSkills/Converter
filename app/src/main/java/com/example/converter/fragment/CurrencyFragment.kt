package com.example.converter.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converter.Common.Common
import com.example.converter.adaptersCurrencies.ConvertAdapterX
import com.example.converter.adaptersCurrencies.ItemModelX
import com.example.converter.adaptersCurrencies.ResponseCurrencies
import com.example.converter.databinding.FragmentCurrencyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrencyFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyBinding
    private lateinit var adapter: ConvertAdapterX


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcViewX()
    }

    private fun initRcViewX() = with(binding) {
        recyclerCurrency.layoutManager = LinearLayoutManager(activity)
        adapter = ConvertAdapterX()
        recyclerCurrency.adapter = adapter
        getAllInformationList()
    }
    private fun getAllInformationList(){
        Common.api.getInformationListX().enqueue(object : Callback<ResponseCurrencies> {
           override fun onFailure(call: Call<ResponseCurrencies>, t: Throwable) {
               Log.d("commontag", "${t}")
           }

           override fun onResponse(
               call: Call<ResponseCurrencies>,
               response: Response<ResponseCurrencies>
           ) {
               Log.d("responsetag", "OK 3")
               val response = response.body()?.response
               val itemModels = response?.let {
                   listOf(
                       ItemModelX(countries = it.fiats.KZT.countries,
                       code = it.fiats.KZT.currency_code,
                       name = it.fiats.KZT.currency_name),
                   ItemModelX (countries = it.fiats.RUB.countries,
                       code = it.fiats.RUB.currency_code,
                   name = it.fiats.RUB.currency_name),
                       ItemModelX (countries = it.fiats.HKD.countries,
                           code = it.fiats.HKD.currency_code,
                           name = it.fiats.HKD.currency_name))
               }
               adapter.submitList(itemModels)
               Log.d("responsetag", "${itemModels}")
           }
       })
    }

    companion object {

        @JvmStatic
        fun newInstance() = CurrencyFragment()
    }
}
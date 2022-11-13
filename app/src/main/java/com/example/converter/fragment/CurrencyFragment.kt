package com.example.converter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converter.adaptersCurrencies.ConvertAdapterX
import com.example.converter.databinding.FragmentCurrencyBinding


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

    }


    companion object {

        @JvmStatic
        fun newInstance() = CurrencyFragment()
    }
}
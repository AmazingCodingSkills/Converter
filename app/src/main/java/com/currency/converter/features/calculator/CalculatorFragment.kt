package com.currency.converter.features.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.currency.converter.features.favorite.CurrenciesAdapter
import com.example.converter.databinding.FragmentCurrencyBinding

class CalculatorFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyBinding
    private lateinit var adapter: CurrenciesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }




    companion object {

        @JvmStatic
        fun newInstance() = CalculatorFragment()
    }
}
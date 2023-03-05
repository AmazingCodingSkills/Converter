package com.currency.converter.features.rate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.converter.databinding.FragmentSelectCurrencyBinding

class SelectCurrencyFragment() : Fragment() {

    private lateinit var binding: FragmentSelectCurrencyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectCurrencyBinding.inflate(inflater, container, false)
        val title = arguments?.getString("value")
        binding.selectCurrency.text = title
        return binding.root
    }

    private val viewModel: SelectCurrencyViewModel by viewModels {
        FactorySelectCurrencyViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is SelectCurrencyViewState.Content -> {}
                }
            }
        }
    }
}

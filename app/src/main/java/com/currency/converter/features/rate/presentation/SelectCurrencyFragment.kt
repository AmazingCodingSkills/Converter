package com.currency.converter.features.rate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.currency.converter.ConverterApplication
import com.currency.converter.features.rate.di.DaggerSelectCurrencyComponent
import com.currency.converter.features.rate.di.SelectCurrencyComponent
import com.example.converter.databinding.FragmentSelectCurrencyBinding

class SelectCurrencyFragment() : Fragment() {

    private lateinit var binding: FragmentSelectCurrencyBinding

        private val component: SelectCurrencyComponent by lazy {
            DaggerSelectCurrencyComponent.factory()
                .create(((activity?.applicationContext as? ConverterApplication)?.appComponent!!))
        }
    private var argOne : String? = null
    private var argTwo : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectCurrencyBinding.inflate(inflater, container, false)
        //val title = requireArguments().getParcelable<RateItem>("value")
        argOne = arguments?.getString("reference")
        argTwo =  arguments?.getString("base")
        binding.selectCurrency.text = argOne+argTwo
        return binding.root
    }

    private val viewModel: SelectCurrencyViewModel by viewModels {
        component.factorySelectCurrencyViewModel().create(argOne!!,argTwo!!)
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

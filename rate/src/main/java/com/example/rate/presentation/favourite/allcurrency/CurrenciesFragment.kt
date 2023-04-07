package com.example.rate.presentation.favourite.allcurrency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.converter.core.ConverterApplication
import com.example.rate.databinding.FragmentTabLayoutFavoritesAllBinding
import com.example.rate.di.CurrenciesComponent
import com.example.rate.di.DaggerCurrenciesComponent
import com.example.rate.presentation.favourite.CurrenciesAdapter



internal class CurrenciesFragment() : Fragment() {

    private lateinit var binding: FragmentTabLayoutFavoritesAllBinding
    private lateinit var adapterCurrencies: CurrenciesAdapter


    private val component: CurrenciesComponent by lazy {
        DaggerCurrenciesComponent.factory()
            .create(((activity?.applicationContext as? ConverterApplication)?.appComponent!!))
    }

    private val viewModel: CurrenciesViewModel by viewModels {
        component.currenciesViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabLayoutFavoritesAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerListCurrency.apply {
            layoutManager = LinearLayoutManager(activity)
            adapterCurrencies = CurrenciesAdapter(onItemClickListener = { item ->
                viewModel.handleAction(CurrenciesViewAction.UpdateList, item)
            })

            binding.recyclerListCurrency.adapter = adapterCurrencies

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.state.collect { state ->
                    when (state) {
                        is CurrenciesViewState.Content -> {
                            adapterCurrencies.submitList(state.items)
                        }
                        else -> {
                        }
                    }
                }
            }
            this.itemAnimator = null
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateScreen()
    }

    companion object {
        fun newInstance() = CurrenciesFragment()
    }
}
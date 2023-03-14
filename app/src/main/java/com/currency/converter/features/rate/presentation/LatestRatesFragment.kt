package com.currency.converter.features.rate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.ConverterApplication.PreferencesManager.MY_REQUEST_KEY
import com.currency.converter.base.network.NetworkAvailabilityDialogFragment
import com.currency.converter.base.showToast
import com.currency.converter.features.favorite.MainFavoriteFragment
import com.currency.converter.features.rate.di.DaggerLatestRatesComponent
import com.currency.converter.features.rate.di.LatestRatesComponent
import com.example.converter.R
import com.example.converter.databinding.FragmentLatestValueBinding
import javax.inject.Inject


class LatestRatesFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentLatestValueBinding
    private lateinit var latestRatesAdapter: LatestRatesAdapter


    private val component: LatestRatesComponent by lazy {
        DaggerLatestRatesComponent.factory()
            .create(((activity?.applicationContext as? ConverterApplication)?.appComponentCreate!!))
    }

    private val viewModel: RatesViewModel by viewModels {
        component.factoryRatesViewModel()
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
        binding.swipeToRefreshMainScreen.setOnRefreshListener {
            viewModel.handleAction(RatesViewAction.SelectCurrency)
        }
        binding.changeCurrencyButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.bottom_navigation_container, MainFavoriteFragment())
                addToBackStack(null)
                commit()
            }
        }
        binding.buttonOpenBottomSheetMainScreen.setOnClickListener {
            val bottomSheet = BaseCurrency()
            bottomSheet.show(parentFragmentManager, "TAG")
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is RatesViewState.Content -> {
                        latestRatesAdapter.submitList(state.items)
                        binding.progressBarMainScreen.visibility = View.GONE
                        binding.buttonOpenBottomSheetMainScreen.setImageResource(state.icon)
                        binding.swipeToRefreshMainScreen.isRefreshing = false
                    }
                    else -> {
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collect { state ->
                when (state) {
                    is RatesViewEvent.ShowErrorDialog -> {
                        showDialogWarning()
                        binding.swipeToRefreshMainScreen.isRefreshing = false
                        context?.showToast(
                            RatesViewEvent.ShowErrorDialog.toString(),
                            Toast.LENGTH_LONG
                        )
                    }
                }
            }
        }
    }

    private fun initRcView() = with(binding) {
        recyclerMainScreen.layoutManager = LinearLayoutManager(activity)
        latestRatesAdapter = LatestRatesAdapter { item ->
            val selectCurrencyFragment = SelectCurrencyFragment()
            val bundle = Bundle().apply {
                putString("reference", item.referenceCurrency.name)
                putString("base", item.baseCurrencyName)
            }
            selectCurrencyFragment.arguments = bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.bottom_navigation_container, selectCurrencyFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        recyclerMainScreen.adapter = latestRatesAdapter

        parentFragmentManager.setFragmentResultListener(
            MY_REQUEST_KEY,
            viewLifecycleOwner
        ) { key, result ->
            val data = result.getSerializable("data")
            if (data != null) {
                viewModel.handleAction(RatesViewAction.UpdateCurrency)
            }

        }
    }

    companion object {
        fun newInstance() = LatestRatesFragment()
    }

    private fun showDialogWarning() {
        val networkAvailabilityDialogFragment = NetworkAvailabilityDialogFragment().apply {
            onReload = {
                viewModel.loadRates()
            }
        }
        networkAvailabilityDialogFragment.show(childFragmentManager, "Dialog")
    }
}

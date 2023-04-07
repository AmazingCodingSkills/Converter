package com.example.rate.presentation.latestrates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.converter.core.Constants.MY_REQUEST_KEY
import com.converter.core.ConverterApplication
import com.converter.core.network.presentation.NetworkAvailabilityDialogFragment
import com.example.rate.databinding.FragmentLatestValueBinding
import javax.inject.Inject
import com.converter.core.R
import com.example.rate.di.DaggerLatestRatesComponent
import com.example.rate.di.LatestRatesComponent
import com.example.rate.presentation.basecurrency.BaseCurrency
import com.example.rate.presentation.favourite.MainFavoriteFragment
import com.example.rate.presentation.selectcurrency.SelectCurrencyFragment


class LatestRatesFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentLatestValueBinding

    private lateinit var latestRatesAdapter: LatestRatesAdapter


    private val component: LatestRatesComponent by lazy {
        DaggerLatestRatesComponent.factory()
            .create(((activity?.applicationContext as? ConverterApplication)?.appComponent!!))
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
        binding.toolbarMainScreen.setFavouriteCurrencyListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).apply {
                    replace(R.id.bottom_navigation_container, MainFavoriteFragment())
                    addToBackStack(null)
                    commit()
                }
        }
        binding.toolbarMainScreen.setCountryClickListener {
            val bottomSheet = BaseCurrency()
            bottomSheet.show(parentFragmentManager, "TAG")
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is RatesViewState.Content -> {
                        latestRatesAdapter.submitList(state.items)
                        binding.progressBarMainScreen.visibility = View.GONE
                        binding.toolbarMainScreen.setBaseCountryBtn(state.icon)
                        binding.swipeToRefreshMainScreen.isRefreshing = false
                    }
                    is RatesViewState.Empty -> {
                        binding.progressBarMainScreen.isVisible = false
                        binding.hintEmptyRateScreen.isVisible = true
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
                        binding.progressBarMainScreen.visibility = View.GONE
                        binding.hintEmptyRateScreen.isVisible = true

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
                putString("value", item.referenceCurrency.value.toString())
            }
            selectCurrencyFragment.arguments = bundle
            val transaction =
                requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                    com.example.rate.R.anim.fragment_slide_left_enter,
                    com.example.rate.R.anim.fragment_slide_left_exit,
                    com.example.rate.R.anim.fragment_slide_left_enter,
                    com.example.rate.R.anim.fragment_slide_left_exit
                )
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

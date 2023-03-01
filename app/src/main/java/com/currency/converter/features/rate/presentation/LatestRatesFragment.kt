package com.currency.converter.features.rate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.base.Observer
import com.currency.converter.base.SelectedCurrencyRepositoryImpl
import com.currency.converter.base.currency.CurrencyRatesRepositoryImpl
import com.currency.converter.base.network.NetworkAvailabilityDialogFragment
import com.currency.converter.base.network.NetworkRepositoryImpl
import com.currency.converter.base.observer.EventBus.subject
import com.currency.converter.features.favorite.MainFavoriteFragment
import com.currency.converter.features.rate.countryname.CountryModel
import com.currency.converter.features.rate.data.FavouriteCurrencyRepositoryImpl
import com.currency.converter.features.rate.domain.UseCaseGetRates
import com.example.converter.R
import com.example.converter.databinding.FragmentLatestValueBinding


class LatestRatesFragment : Fragment() {

    private lateinit var binding: FragmentLatestValueBinding
    private lateinit var latestRatesAdapter: LatestRatesAdapter


    private val viewModel: RatesViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return RatesViewModel(
                    networkRepository = NetworkRepositoryImpl(ConverterApplication.application),
                    selectedCurrencyRepository = SelectedCurrencyRepositoryImpl(),
                    useCaseGetRates = UseCaseGetRates(
                        FavouriteCurrencyRepositoryImpl(),
                        CurrencyRatesRepositoryImpl()
                    )
                ) as T
            }
        }
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
            bottomSheet.show(childFragmentManager, "TAG")
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
                        showToast(RatesViewEvent.ShowErrorDialog.toString())
                    }
                }
            }
        }
    }

    private fun initRcView() = with(binding) {
        recyclerMainScreen.layoutManager = LinearLayoutManager(activity)
        latestRatesAdapter = LatestRatesAdapter()
        recyclerMainScreen.adapter = latestRatesAdapter
        subject.addObserver(object : Observer<CountryModel?> {

            override fun update(value: CountryModel?) {
                if (value != null) {
                    viewModel.handleAction(RatesViewAction.UpdateCurrency)
                }
            }
        })
    }

    companion object {
        fun newInstance() = LatestRatesFragment()
    }

    private fun showDialogWarning() {
        val networkAvailabilityDialogFragment = NetworkAvailabilityDialogFragment()
        networkAvailabilityDialogFragment.show(childFragmentManager, "Dialog")
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}

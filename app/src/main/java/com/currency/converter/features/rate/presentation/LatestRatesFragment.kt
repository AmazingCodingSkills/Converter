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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class LatestRatesFragment : Fragment(), RateView {

    private lateinit var binding: FragmentLatestValueBinding
    private lateinit var latestRatesAdapter: LatestRatesAdapter
    /*private val presenter = RatesPresenter(
        networkRepository = NetworkRepositoryImpl(ConverterApplication.application),
        selectedCurrencyRepository = SelectedCurrencyRepositoryImpl(),
        useCaseGetRates = UseCaseGetRates(
            FavouriteCurrencyRepositoryImpl(),
            CurrencyRatesRepositoryImpl()
        )
    )*/

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
        viewModel.handleAction(RatesViewAction.SelectCurrency)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //presenter.attachView(this)
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

        viewModel.state.onEach { state ->
            when (state) {
                is RatesViewState.Content -> {
                    latestRatesAdapter.submitList(state.items)
                    binding.progressBarMainScreen.visibility = View.GONE
                    binding.buttonOpenBottomSheetMainScreen.setImageResource(state.icon)
                    binding.swipeToRefreshMainScreen.isRefreshing = false
                }
                is RatesViewState.Error -> {
                    showDialogWarning()
                    binding.swipeToRefreshMainScreen.isRefreshing = false
                    showToast(RatesViewState.Error.toString())
                }
                else -> {
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initRcView() = with(binding) {
        recyclerMainScreen.layoutManager = LinearLayoutManager(activity)
        latestRatesAdapter = LatestRatesAdapter()
        recyclerMainScreen.adapter = latestRatesAdapter
        subject.addObserver(object : Observer<CountryModel?> {

            override fun update(value: CountryModel?) {
                if (value != null) {
                    viewModel.handleAction(RatesViewAction.UpdateCurrency(value.baseCurrency,value.icon))
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleAction(RatesViewAction.SelectCurrency)
    }



    companion object {
        fun newInstance() = LatestRatesFragment()
    }

   /* override fun showRates(list: List<RateItem>?) {
        latestRatesAdapter.submitList(list)
    }*/

    /*override fun showIcon(icon: Int) {
        binding.buttonOpenBottomSheetMainScreen.setImageResource(icon)
    }*/


    private fun showDialogWarning() {
        val networkAvailabilityDialogFragment = NetworkAvailabilityDialogFragment()
        networkAvailabilityDialogFragment.show(childFragmentManager, "Dialog")
    }

    /*override fun showProgress() {
        binding.progressBarMainScreen.visibility = View.VISIBLE
    }*/

    /*override fun hideProgress() {
        binding.progressBarMainScreen.visibility = View.GONE
    }*/

     private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


    /*override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }*/
}

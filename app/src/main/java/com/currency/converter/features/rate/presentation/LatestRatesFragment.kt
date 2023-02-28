package com.currency.converter.features.rate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.base.*
import com.currency.converter.base.EventBus.subject
import com.currency.converter.features.favorite.MainFavoriteFragment
import com.currency.converter.features.rate.countryname.CountryModel
import com.currency.converter.features.rate.domain.RateItem
import com.example.converter.R
import com.example.converter.databinding.FragmentLatestValueBinding


class LatestRatesFragment : Fragment(), RateView {

    private lateinit var binding: FragmentLatestValueBinding
    private lateinit var latestRatesAdapter: LatestRatesAdapter
    private val presenter = RatesPresenter(
        networkRepositoryImpl = NetworkRepositoryImpl(ConverterApplication.application),
        selectedCurrencyRepository = SelectedCurrencyRepositoryImpl(),
        currencyRatesRepository = CurrencyRatesRepositoryImpl(
            RateItemMapper()
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLatestValueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        binding.swipeToRefreshMainScreen.setOnRefreshListener {
            presenter.onRefreshed()
        }
        initRcView()
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
    }

    private fun initRcView() = with(binding) {
        recyclerMainScreen.layoutManager = LinearLayoutManager(activity)
        latestRatesAdapter = LatestRatesAdapter()
        recyclerMainScreen.adapter = latestRatesAdapter
        subject.addObserver(object : Observer<CountryModel?> {

            override fun update(value: CountryModel?) {
                if (value != null) {
                    presenter.onSelectedCurrencyShowed(value.baseCurrency, value.icon)

                }
            }
        })
        presenter.onSavedCurrencyGated()
    }

    companion object {
        fun newInstance() = LatestRatesFragment()
    }

    override fun showRates(list: List<RateItem>?) {
        latestRatesAdapter.submitList(list)
    }

    override fun changeRates(base: String, icon: Int) {
        presenter.onSelectedCurrencyShowed(base, icon)
    }

    override fun showIcon(icon: Int) {
        binding.buttonOpenBottomSheetMainScreen.setImageResource(icon)
    }

    override fun showRefreshing(refreshing: Boolean) {
        binding.swipeToRefreshMainScreen.isRefreshing = refreshing

    }

    override fun showDialogWarning() {
        val networkAvailabilityDialogFragment = NetworkAvailabilityDialogFragment()
        networkAvailabilityDialogFragment.show(childFragmentManager, "Dialog")
    }

    override fun showProgress() {
        binding.progressBarMainScreen.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBarMainScreen.visibility = View.GONE
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}

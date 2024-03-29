package com.currency.converter.rate

import app.cash.turbine.test
import com.currency.converter.CoroutineDispatchersRule
import com.converter.core.network.domain.NetworkRepository
import com.converter.core.country.data.CountryModel
import com.converter.core.currency.domain.Currency
import com.converter.core.currency.domain.RateItem
import com.converter.core.favorite.domain.FavouriteCurrencyRepository
import com.example.rate.presentation.selectcurrency.SelectedCurrencyRepository
import com.example.rate.domain.UseCaseGetRates
import com.example.rate.presentation.latestrates.RatesViewAction
import com.example.rate.presentation.latestrates.RatesViewEvent
import com.example.rate.presentation.latestrates.RatesViewModel
import com.example.rate.presentation.latestrates.RatesViewState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RatesViewModelTest {

    @get:Rule
    var rule = CoroutineDispatchersRule()


    private val networkRepository: NetworkRepository = mock()
    private val selectedCurrencyRepository: SelectedCurrencyRepository = mock()
    private val useCaseGetRates: UseCaseGetRates = mock()
    private val favouriteCurrencyRepository: FavouriteCurrencyRepository = mock()
    private lateinit var viewModel: RatesViewModel

    private val countryModel = CountryModel(
        nameCountry = "USA",
        baseCurrency = "USD",
        icon = 123
    )

    private val allCurrencyRates = listOf(
        RateItem(
            date = Calendar.getInstance().time,
            baseCurrencyName = "USD",
            referenceCurrency = Currency("EUR", 0.9),
        ),
        RateItem(
            date = Calendar.getInstance().time,
            baseCurrencyName = "USD",
            referenceCurrency = Currency("RUB", 75.5),
        )
    )


    @Before
    fun setup() {
        runBlocking {
            whenever(networkRepository.isInternetAvailable()).thenReturn(true)
            whenever(selectedCurrencyRepository.selectedCurrency()).thenReturn(countryModel)
            whenever(useCaseGetRates.invoke("USD")).thenReturn(allCurrencyRates)
        }
    }

    @Test
    fun `WHEN init Should load initial state with all currency rates`() = runTest {
        initViewModel()

        viewModel.state.test {
            assertEquals(RatesViewState.Content(allCurrencyRates, 123), awaitItem())
        }
    }

    @Test
    fun `WHEN refresh Should reload currency rates`() = runTest {
        initViewModel()

        viewModel.state.test {
            viewModel.handleAction(RatesViewAction.SelectCurrency)

            assertEquals(RatesViewState.Content(allCurrencyRates, 123), awaitItem())
        }
    }

    @Test
    fun `WHEN change currency Should recalculate currency rates`() = runTest {

        initViewModel()

        viewModel.state.test {
            viewModel.handleAction(RatesViewAction.UpdateCurrency)

            assertEquals(RatesViewState.Content(allCurrencyRates, 123), awaitItem())
        }
    }

    @Test
    fun `WHEN no internet connection Should show error`() = runTest {
        whenever(networkRepository.isInternetAvailable()).thenReturn(false)

        initViewModel()

        viewModel.events.test {
            assertEquals(RatesViewEvent.ShowErrorDialog, awaitItem())
        }
    }


    @Test
    fun `WHEN selected currency is empty Should show error`() = runTest {
        whenever(selectedCurrencyRepository.selectedCurrency()).thenReturn(null)

        initViewModel()

        viewModel.events.test {
            assertEquals(RatesViewEvent.ShowErrorDialog, awaitItem())
        }
    }

    private fun initViewModel() {
        viewModel = RatesViewModel(
            networkRepository = networkRepository,
            selectedCurrencyRepository = selectedCurrencyRepository,
            useCaseGetRates = useCaseGetRates,
            favouriteCurrencyRepository = favouriteCurrencyRepository
        )
    }
}

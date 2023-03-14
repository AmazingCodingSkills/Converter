package com.currency.converter.rate

import app.cash.turbine.test
import com.currency.converter.CoroutineDispatchersRule
import com.currency.converter.base.network.NetworkRepository
import com.currency.converter.features.calculator.domain.UseCaseGetCurrentRates
import com.currency.converter.features.calculator.presentation.CalculatorViewAction
import com.currency.converter.features.calculator.presentation.CalculatorViewEvent
import com.currency.converter.features.calculator.presentation.CalculatorViewModel
import com.currency.converter.features.calculator.presentation.CalculatorViewState
import junit.framework.Assert.assertEquals
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


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CalculatorViewModelTest {

    @get:Rule
    var rule = CoroutineDispatchersRule()

    private val networkRepository: NetworkRepository = mock()
    private val useCaseGetCurrentRates: UseCaseGetCurrentRates = mock()

    private var viewModel = CalculatorViewModel(
        networkRepository = networkRepository,
        useCaseGetCurrentRates = useCaseGetCurrentRates
    )

    private val input = "100"
    private val selectFrom = "RUB"
    private val baseFrom = "USD"
    private val baseTo = "EUR"
    private val selectTo = "AZN"
    private val currencyRate = 0.83
    private val value = input.toDouble() * currencyRate

    @Before
    fun setup() {
        runBlocking {
            whenever(networkRepository.isInternetAvailable()).thenReturn(true)
            whenever(useCaseGetCurrentRates.getCurrentRate(selectFrom, selectTo)).thenReturn(
                currencyRate
            )
        }
    }


    @Test
    fun `WHEN open tab calculator Should be installed base currency`() = runBlocking {

        viewModel.state.test {
            assertEquals(
                CalculatorViewState.Content(
                    from = baseFrom,
                    resultFrom = null,
                    resultTo = null,
                    to = baseTo
                ), awaitItem()
            )
        }

    }


    @Test
    fun `WHEN change currency FROM should  set selected currency FROM`() = runBlocking {

        viewModel.state.test {
            viewModel.handleAction(CalculatorViewAction.CurrencySetFrom(selectFrom, ""))

            skipItems(1)

            assertEquals(
                CalculatorViewState.Content(
                    from = selectFrom,
                    resultFrom = null,
                    resultTo = null,
                    to = baseTo
                ), awaitItem()
            )
        }

    }

    @Test
    fun `WHEN change currency TO should  set selected currency TO`() = runBlocking {

        viewModel.state.test {
            viewModel.handleAction(CalculatorViewAction.CurrencySetTo(selectTo, ""))

            skipItems(1)

            assertEquals(
                CalculatorViewState.Content(
                    from = baseFrom,
                    resultFrom = null,
                    resultTo = null,
                    to = selectTo
                ), awaitItem()
            )
        }

    }


    @Test
    fun `WHEN input changed FROM should calculate input TO`() = runBlocking {
        viewModel.state.test {
            viewModel.handleAction(CalculatorViewAction.CurrencySetFrom(selectFrom, ""))
            viewModel.handleAction(CalculatorViewAction.CurrencySetTo(selectTo, ""))
            viewModel.handleAction(CalculatorViewAction.CurrencyConvertedOne(input))

            skipItems(3)

            assertEquals(
                CalculatorViewState.Content(
                    resultFrom = value,
                    resultTo = null,
                    from = selectFrom,
                    to = selectTo
                ), awaitItem()
            )
        }
    }

    @Test
    fun `WHEN input changed TO should calculate input FROM`() = runBlocking {
        viewModel.state.test {
            viewModel.handleAction(CalculatorViewAction.CurrencySetTo(selectFrom, ""))
            viewModel.handleAction(CalculatorViewAction.CurrencySetFrom(selectTo, ""))
            viewModel.handleAction(CalculatorViewAction.CurrencyConvertedTwo(input))

            skipItems(3)

            assertEquals(
                CalculatorViewState.Content(
                    resultFrom = null,
                    resultTo = value,
                    from = selectTo,
                    to = selectFrom
                ), awaitItem()
            )
        }
    }


    @Test
    fun `WHEN no internet connection Should show error`() = runTest {
        whenever(networkRepository.isInternetAvailable()).thenReturn(false)

        viewModel.events.test {
            viewModel.handleAction(CalculatorViewAction.InternetError)
            assertEquals(CalculatorViewEvent.ShowErrorDialog, awaitItem())
        }
    }
}



package com.currency.converter.rate

import app.cash.turbine.test
import com.currency.converter.CoroutineDispatchersRule
import com.converter.core.network.NetworkRepository
import com.example.calculator.domain.UseCaseGetCurrentRates
import com.example.calculator.presentation.CalculatorViewAction
import com.example.calculator.presentation.CalculatorViewEvent
import com.example.calculator.presentation.CalculatorViewModel
import com.example.calculator.presentation.CalculatorViewState
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

    private var viewModel = com.example.calculator.presentation.CalculatorViewModel(
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
                com.example.calculator.presentation.CalculatorViewState.Content(
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
            viewModel.handleAction(com.example.calculator.presentation.CalculatorViewAction.CurrencySetFrom(selectFrom, ""))

            skipItems(1)

            assertEquals(
                com.example.calculator.presentation.CalculatorViewState.Content(
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
            viewModel.handleAction(com.example.calculator.presentation.CalculatorViewAction.CurrencySetTo(selectTo, ""))

            skipItems(1)

            assertEquals(
                com.example.calculator.presentation.CalculatorViewState.Content(
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
            viewModel.handleAction(com.example.calculator.presentation.CalculatorViewAction.CurrencySetFrom(selectFrom, ""))
            viewModel.handleAction(com.example.calculator.presentation.CalculatorViewAction.CurrencySetTo(selectTo, ""))
            viewModel.handleAction(com.example.calculator.presentation.CalculatorViewAction.CurrencyConvertedOne(input))

            skipItems(3)

            assertEquals(
                com.example.calculator.presentation.CalculatorViewState.Content(
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
            viewModel.handleAction(com.example.calculator.presentation.CalculatorViewAction.CurrencySetTo(selectFrom, ""))
            viewModel.handleAction(com.example.calculator.presentation.CalculatorViewAction.CurrencySetFrom(selectTo, ""))
            viewModel.handleAction(com.example.calculator.presentation.CalculatorViewAction.CurrencyConvertedTwo(input))

            skipItems(3)

            assertEquals(
                com.example.calculator.presentation.CalculatorViewState.Content(
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
            viewModel.handleAction(com.example.calculator.presentation.CalculatorViewAction.InternetError)
            assertEquals(com.example.calculator.presentation.CalculatorViewEvent.ShowErrorDialog, awaitItem())
        }
    }
}



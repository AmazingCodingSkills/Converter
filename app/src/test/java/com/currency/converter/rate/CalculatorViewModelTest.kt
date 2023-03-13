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

    private var viewModel =  CalculatorViewModel(
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
            whenever(useCaseGetCurrentRates.getCurrentRate(selectFrom, selectTo)).thenReturn(currencyRate)
        }
    }

    @Test
    fun `WHEN should it set the value in the FROM`() = runBlocking {

        viewModel.state.test {
            viewModel.handleAction(CalculatorViewAction.CurrencySetFrom(selectFrom, ""))
            //viewModel.handleAction(CalculatorViewAction.CurrencySetTo(to, ""))
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
    fun `WHEN should it set the value in the TO`() = runBlocking {

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

    // мне понадобилось ввести переменные с изначально использованными валютами
    // если этого не сделать нужной реализации достичь не получиться, можно сделать один общий метод
    // но я думаю, это не совсем то, что нужно


    // nameing
    @Test
    fun `WHEN input changed FROM should calculate input TO`() = runBlocking {
        viewModel.state.test {
            viewModel.handleAction(CalculatorViewAction.CurrencySetFrom(selectFrom,""))
            viewModel.handleAction(CalculatorViewAction.CurrencySetTo(selectTo,""))
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
    fun `WHEN no internet connection Should show error`() = runTest {
        whenever(networkRepository.isInternetAvailable()).thenReturn(false)

        viewModel.events.test {
            viewModel.handleAction(CalculatorViewAction.InternetError)
            assertEquals(CalculatorViewEvent.ShowErrorDialog, awaitItem())
        }
    }
}


/*class CalculatorViewModelTest {

    // Здесь нужно создать фиктивные зависимости для NetworkRepository и UseCaseGetCurrentRates,
    // которые будут возвращать предопределенные результаты, чтобы мы могли тестировать
    // взаимодействие с ними.

    private val networkRepository = mockk<NetworkRepository>()
    private val useCaseGetCurrentRates = mockk<UseCaseGetCurrentRates>()

    private val viewModel = CalculatorViewModel(networkRepository, useCaseGetCurrentRates)

    @Test
    fun `test setFrom() function`() {
        viewModel.setFrom("EUR", "100")
        val expectedState = CalculatorViewState.Content(null, null, "EUR", "EUR")
        assertEquals(expectedState, viewModel.state.value)

        viewModel.setFrom("USD", "50")
        val expectedState2 = CalculatorViewState.Content(null, null, "USD", "EUR")
        assertEquals(expectedState2, viewModel.state.value)
    }

    @Test
    fun `test setTo() function`() {
        viewModel.setTo("GBP", "100")
        val expectedState = CalculatorViewState.Content(null, null, "USD", "GBP")
        assertEquals(expectedState, viewModel.state.value)

        viewModel.setTo("CAD", "50")
        val expectedState2 = CalculatorViewState.Content(null, null, "USD", "CAD")
        assertEquals(expectedState2, viewModel.state.value)
    }

    @Test
    fun `test onTextInputChanged() function with valid input`() {
        viewModel.onTextInputChanged("50",*/


/*class CalculatorViewModelTest {

    @Mock
    private lateinit var mockNetworkRepository: NetworkRepository

    @Mock
    private lateinit var mockUseCaseGetCurrentRates: UseCaseGetCurrentRates

    private lateinit var calculatorViewModel: CalculatorViewModel

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        calculatorViewModel = CalculatorViewModel(mockNetworkRepository, mockUseCaseGetCurrentRates)
    }

    @Test
    fun `test setFrom`() = runBlocking {
        val input = "100"
        val base = "EUR"
        val to = "USD"

        calculatorViewModel.handleAction(CalculatorViewAction.CurrencySetFrom(base, input))

        assertEquals(
            CalculatorViewState.Content(
                resultFrom = input.toDouble(),
                resultTo = null,
                from = base,
                to = to
            ),
            calculatorViewModel.state.first()
        )
    }

    @Test
    fun `test setTo`() = runBlocking {
        val input = "100"
        val from = "USD"
        val referenceCurrency = "EUR"

        calculatorViewModel.handleAction(CalculatorViewAction.CurrencySetTo(referenceCurrency, input))

        assertEquals(
            CalculatorViewState.Content(
                resultFrom = null,
                resultTo = input.toDouble(),
                from = from,
                to = referenceCurrency
            ),
            calculatorViewModel.state.first()
        )
    }

    @Test
    fun `test convertCurrency with internet connection`() = runBlocking {
        val input = "100"
        val baseCurrencyCode = "USD"
        val referenceCurrencyCode = "EUR"
        val currentRate = 0.83

        `when`(mockNetworkRepository.isInternetAvailable()).thenReturn(true)
        `when`(mockUseCaseGetCurrentRates.getCurrentRate(baseCurrencyCode, referenceCurrencyCode)).thenReturn(currentRate)

        calculatorViewModel.convertCurrency(baseCurrencyCode, input, referenceCurrencyCode)

        assertEquals(
            CalculatorViewState.Content(
                resultFrom = null,
                resultTo = input.toDouble() * currentRate,
                from = baseCurrencyCode,
                to = referenceCurrencyCode
            ),
            calculatorViewModel.state.first()
        )
    }

    @Test
    fun `test convertCurrency without internet connection`() = runBlocking {
        val input = "100"
        val baseCurrencyCode = "USD"
        val referenceCurrencyCode = "EUR"
        val exception = IOException("No internet connection")

        `when`(mockNetworkRepository.isInternetAvailable()).thenReturn(false)

        calculatorViewModel.convertCurrency(baseCurrencyCode, input, referenceCurrencyCode)

        assertEquals(
            CalculatorViewEvent.ShowErrorDialog,
            calculatorViewModel.events.first()
        )
    }

    @Test
    fun `test convertCurrency with exception`() = runBlocking {
        val input = "100"
        val baseCurrencyCode = "USD"
        val referenceCurrencyCode = "EUR"
        val exception = IOException("No internet connection")

        `when`(mockNetworkRepository.isInternetAvailable()).thenReturn(true)
        `when`(mockUseCaseGetCurrentRates.getCurrentRate(base*/
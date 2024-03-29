package com.example.calculator.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.converter.core.ConverterApplication
import com.converter.core.currency.domain.CurrencyItem
import com.converter.core.view.hideKeyboard
import com.converter.core.network.presentation.NetworkAvailabilityDialogFragment
import com.converter.core.view.showKeyboard
import com.example.calculator.di.CalculatorComponent
import com.example.calculator.databinding.FragmentConverterBinding
import com.example.calculator.di.DaggerCalculatorComponent
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class CalculatorFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding

    private val component: CalculatorComponent by lazy {
        DaggerCalculatorComponent.factory()
            .create((activity?.applicationContext as? ConverterApplication)?.appComponent!!)
    }

    private val viewModel: CalculatorViewModel by viewModels {
        component.factoryCalculatorViewModel()
    }

    private lateinit var textWatcherOne: TextWatcher
    private lateinit var textWatcherTwo: TextWatcher
    private var firstSetText: EditText? = null
    private var secondSetText: EditText? = null

    private val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        decimalSeparator = '.'
    }
    private val entryFormat = DecimalFormat("#####.###", symbols)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleAction(CalculatorViewAction.InternetError)
        binding.firstEditText.requestFocus()
        binding.firstEditText.showKeyboard()
        firstSetText = binding.firstEditText
        secondSetText = binding.secondEditText
        clickSearchButtons()
        observedChangesScreen()

    }


    private fun observedChangesScreen() {

        textWatcherOne = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = firstSetText?.text.toString()
                viewModel.handleAction(CalculatorViewAction.CurrencyConvertedOne(input))
            }

            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }

        textWatcherTwo = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = secondSetText?.text.toString()
                viewModel.handleAction(CalculatorViewAction.CurrencyConvertedTwo(input))
            }

            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }

        firstSetText?.addTextChangedListener(textWatcherOne)
        secondSetText?.addTextChangedListener(textWatcherTwo)

        setFragmentResultListener("CURRENCY_KEY") { requestKey, bundle ->
            Log.d("TAG", "Result = $requestKey, $bundle")
            val tag = bundle.getString("TAG")
            val item = bundle.getParcelable<CurrencyItem>("SELECTED_CURRENCY")
                ?: throw IllegalStateException("Selected currency is empty")
            when (tag) {
                "FROM" -> viewModel.handleAction(
                    CalculatorViewAction.CurrencySetFrom(
                        item.id,
                        binding.firstEditText.text.toString()
                    )
                )
                "TO" -> viewModel.handleAction(
                    CalculatorViewAction.CurrencySetTo(
                        item.id,
                        binding.secondEditText.text.toString()
                    )
                )
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is CalculatorViewState.Content -> {
                        state.resultFrom?.let { setResultOneConversion(it) }
                        state.resultTo?.let { setResultTwoConversion(it) }
                        setCurrenciesCode(state.to, state.from)
                    }
                    is CalculatorViewState.Empty -> {
                        clearFrom()
                        clearTo()
                        setCurrenciesCode(state.defaultFrom, state.defaultTo)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collect { events ->
                when (events) {
                    is CalculatorViewEvent.ShowErrorDialog -> {
                        showDialog()
                    }
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()
        binding.firstEditText.hideKeyboard()
    }

    private fun clickSearchButtons() = with(binding) {
        firstCurrency.setOnClickListener {
            val allCurrencyBottomSheet = AllCurrencyBottomSheet()
            allCurrencyBottomSheet.show(childFragmentManager, "FROM")
        }

        secondCurrency.setOnClickListener {
            val allCurrencyBottomSheet = AllCurrencyBottomSheet()
            allCurrencyBottomSheet.show(childFragmentManager, "TO")
        }
    }

    private fun TextView.applyWithDisabledTextWatcher(
        textWatcher: TextWatcher, codeBlock: TextView.() -> Unit
    ) {
        this.removeTextChangedListener(textWatcher)
        codeBlock()
        this.addTextChangedListener(textWatcher)
    }

    companion object {
        fun newInstance() = CalculatorFragment()
    }

    private fun setResultOneConversion(resultOne: Double) {
        binding.secondEditText.applyWithDisabledTextWatcher(textWatcherTwo) {
            text = entryFormat.format(resultOne)
        }
    }

    private fun setResultTwoConversion(resultTwo: Double) {
        binding.firstEditText.applyWithDisabledTextWatcher(textWatcherOne) {
            text = entryFormat.format(resultTwo)
        }
    }

    private fun showDialog() {
        val networkAvailabilityDialogFragment = NetworkAvailabilityDialogFragment()
        networkAvailabilityDialogFragment.show(childFragmentManager, "Dialog")
    }

    private fun setCurrenciesCode(to: String, from: String) {
        binding.firstCurrency.text = from
        binding.secondCurrency.text = to
    }

    private fun clearFrom() {
        binding.secondEditText.applyWithDisabledTextWatcher(textWatcherTwo) {
            text = ""
        }
    }

    private fun clearTo() {
        binding.firstEditText.applyWithDisabledTextWatcher(textWatcherOne) {
            text = ""
        }
    }
}






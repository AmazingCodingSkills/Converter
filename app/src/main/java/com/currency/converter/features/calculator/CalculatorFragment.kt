package com.currency.converter.features.calculator

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
import com.currency.converter.base.CurrencyRatesRepository
import com.currency.converter.features.favorite.CurrencyItem
import com.example.converter.databinding.FragmentConverterBinding
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class CalculatorFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding
    private var from = "USD"
    private var to = "EUR"
    private lateinit var textWatcherOne: TextWatcher
    private lateinit var textWatcherTwo: TextWatcher
    private val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        decimalSeparator = '.'
    }
    private var entryFormat = DecimalFormat("#####.###", symbols)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickSearchButtons()
    }

    override fun onStart() {
        super.onStart()
        binding.currencyFrom.text = from
        binding.currencyTo.text = to
        val firstEditText = binding.etFirstConversion
        val secondEditText = binding.etSecondConversion

        textWatcherOne = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = firstEditText.text.toString()
                //text.removeAfter2Decimal(firstEditText)

                if (input.isNotEmpty()) {
                    loadCurrentRate(from, input, to, secondEditText, textWatcherTwo)
                } else {
                    secondEditText.applyWithDisabledTextWatcher(textWatcherTwo) {
                        text = ""
                    }
                }
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
                val input = secondEditText.text.toString()
                //text.removeAfter2Decimal(secondEditText)

                if (input.isNotEmpty()) {
                    loadCurrentRate(to, input, from, firstEditText, textWatcherOne)
                } else {
                    firstEditText.applyWithDisabledTextWatcher(textWatcherOne) {
                        text = ""
                    }
                }
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

        firstEditText.addTextChangedListener(textWatcherOne)
        secondEditText.addTextChangedListener(textWatcherTwo)

        setFragmentResultListener("CURRENCY_KEY") { requestKey, bundle ->
            Log.d("TAG", "Result = $requestKey, $bundle")
            val tag = bundle.getString("TAG")
            val item = bundle.getParcelable<CurrencyItem>("SELECTED_CURRENCY")
                ?: throw IllegalStateException("Selected currency is empty")
            when (tag) {
                "FROM" -> setDefaultValueFrom(item.id)
                "TO" -> setDefaultValueTo(item.id)
            }
        }
    }

    private fun loadCurrentRate(
        baseCurrencyCode: String,
        input: String,
        referenceCurrencyCode: String,
        editText: EditText,
        watcher: TextWatcher
    ) {
        val value = input.toDouble()
        CurrencyRatesRepository.getLatestApiResult(baseCurrencyCode,{}) { rates ->
            val findItem = rates?.find { it.referenceCurrency.name == referenceCurrencyCode }
            val findValue = findItem?.referenceCurrency?.value ?: 0.0
            val result = entryFormat.format(value * findValue)
            editText.applyWithDisabledTextWatcher(watcher) {
                text = result
            }
        }
    }

    fun clickSearchButtons() = with(binding) {
        currencyFrom.setOnClickListener {
            val allCurrencyBottomSheet = AllCurrencyBottomSheet()
            allCurrencyBottomSheet.show(childFragmentManager, "FROM")
        }

        currencyTo.setOnClickListener {
            val allCurrencyBottomSheet = AllCurrencyBottomSheet()
            allCurrencyBottomSheet.show(childFragmentManager, "TO")
        }
    }

    private fun setDefaultValueFrom(selectedCurrency: String) {
        if (!isEmptyInput()) {
            loadCurrentRate(
                selectedCurrency,
                binding.etFirstConversion.text.toString(),
                to,
                binding.etSecondConversion,
                textWatcherTwo
            )
        }
        from = selectedCurrency
        binding.currencyFrom.text = selectedCurrency
    }

    private fun setDefaultValueTo(value: String) {
        if (!isEmptyInput()) {
            loadCurrentRate(
                value,
                binding.etSecondConversion.text.toString(),
                to,
                binding.etFirstConversion,
                textWatcherOne
            )
        }
        to = value
        binding.currencyTo.text = value
    }

    private fun isEmptyInput(): Boolean {
        val inputFrom = binding.etFirstConversion.text
        val inputTo = binding.etSecondConversion.text
        return inputFrom.isEmpty() || inputTo.isEmpty()
    }

    fun TextView.applyWithDisabledTextWatcher(
        textWatcher: TextWatcher, codeBlock: TextView.() -> Unit
    ) {
        this.removeTextChangedListener(textWatcher)
        codeBlock()
        this.addTextChangedListener(textWatcher)
    }

    companion object {
        fun newInstance() = CalculatorFragment()
    }
}






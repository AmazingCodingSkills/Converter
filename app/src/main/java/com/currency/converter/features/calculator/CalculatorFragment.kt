package com.currency.converter.features.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.currency.converter.features.calculator.presenter.CalculatorPresenter
import com.currency.converter.features.calculator.view.CalculatorView
import com.currency.converter.features.favorite.CurrencyItem
import com.example.converter.databinding.FragmentConverterBinding
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class CalculatorFragment : Fragment(), CalculatorView {

    private lateinit var binding: FragmentConverterBinding
    private val presenter = CalculatorPresenter()
    private lateinit var textWatcherOne: TextWatcher
    private lateinit var textWatcherTwo: TextWatcher

    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        decimalSeparator = '.'
    }
    val entryFormat = DecimalFormat("#####.###", symbols)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        clickSearchButtons()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStarted()

        val firstEditText = binding.etFirstConversion
        val secondEditText = binding.etSecondConversion

        textWatcherOne = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = firstEditText.text.toString()
                presenter.onTextInputChangedOne(input)
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
                presenter.onTextInputChangedTwo(input)
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

    override fun setDefaultValueFrom(selectedCurrency: String) {
        if (!isEmptyInput()) {
            presenter.setFrom(
                input = binding.etFirstConversion.text.toString(),
                selectedCurrency = selectedCurrency
            )
            binding.currencyFrom.text = selectedCurrency
        }
    }

    override fun setDefaultValueTo(value: String) {
        if (!isEmptyInput()) {
            presenter.setTo(input = binding.etFirstConversion.text.toString(), value = value)
            binding.currencyTo.text = value
        }
    }

    private fun isEmptyInput(): Boolean {
        val inputFrom = binding.etFirstConversion.text
        val inputTo = binding.etSecondConversion.text
        return inputFrom.isEmpty() || inputTo.isEmpty() // возможно вот эту часть так же нужно перенести в презентер
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

    override fun getResultOne(resultOne: Double) {
        binding.etSecondConversion.applyWithDisabledTextWatcher(textWatcherTwo) {
            text = entryFormat.format(resultOne)
        }
    }

    override fun getResultTwo(resultTwo: Double) {
        binding.etFirstConversion.applyWithDisabledTextWatcher(textWatcherOne) {
            text = entryFormat.format(resultTwo)
        }
    }


    override fun setCurrencies(to: String, from: String) {
        binding.currencyFrom.text = from
        binding.currencyTo.text = to
    }

    override fun clearFrom() {
        binding.etSecondConversion.applyWithDisabledTextWatcher(textWatcherTwo) {
            text = ""
        }
    }

    override fun clearTo() {
        binding.etFirstConversion.applyWithDisabledTextWatcher(textWatcherOne) {
            text = ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}






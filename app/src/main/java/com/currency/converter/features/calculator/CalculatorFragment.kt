package com.currency.converter.features.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.currency.converter.ConverterApplication
import com.currency.converter.base.NetworkAvailabilityDialogFragment
import com.currency.converter.base.NetworkRepository
import com.currency.converter.features.calculator.presenter.CalculatorPresenter
import com.currency.converter.features.calculator.view.CalculatorView
import com.currency.converter.features.favorite.CurrencyItem
import com.example.converter.R
import com.example.converter.databinding.FragmentConverterBinding
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class CalculatorFragment : Fragment(), CalculatorView {

    private lateinit var binding: FragmentConverterBinding
    private val presenter = CalculatorPresenter(
        networkRepository = NetworkRepository(
            ConverterApplication.application
        )
    )
    private lateinit var textWatcherOne: TextWatcher
    private lateinit var textWatcherTwo: TextWatcher

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
        presenter.attachView(this)
        presenter.onDialogWarning()
        clickSearchButtons()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStarted()

        val firstEditText = binding.firstEditText
        val secondEditText = binding.secondEditText

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
                "FROM" -> presenter.setFrom(item.id, binding.firstEditText.text.toString())
                "TO" -> presenter.setTo(item.id, binding.secondEditText.text.toString())
            }
        }
    }


    fun clickSearchButtons() = with(binding) {
        firstCurrency.setOnClickListener {
            val allCurrencyBottomSheet = AllCurrencyBottomSheet()
            allCurrencyBottomSheet.show(childFragmentManager, "FROM")
        }

        secondCurrency.setOnClickListener {
            val allCurrencyBottomSheet = AllCurrencyBottomSheet()
            allCurrencyBottomSheet.show(childFragmentManager, "TO")
        }
    }

    override fun setDefaultValueFrom(selectedCurrency: String) {
        binding.firstCurrency.text = selectedCurrency
    }

    override fun setDefaultValueTo(value: String) {
        binding.secondCurrency.text = value
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

    override fun setResultOneConversion(resultOne: Double) {
        binding.secondEditText.applyWithDisabledTextWatcher(textWatcherTwo) {
            text = entryFormat.format(resultOne)
        }
    }

    override fun setResultTwoConversion(resultTwo: Double) {
        binding.firstEditText.applyWithDisabledTextWatcher(textWatcherOne) {
            text = entryFormat.format(resultTwo)
        }
    }

    override fun showDialog() {
        val networkAvailabilityDialogFragment = NetworkAvailabilityDialogFragment()
        networkAvailabilityDialogFragment.show(childFragmentManager, "Dialog")
    }

    override fun showToast(message: Int) {
        Toast.makeText(
            activity,
            context?.getString(R.string.message_for_exception_calculator),
            Toast.LENGTH_LONG
        ).show()
    }


    override fun setCurrencies(to: String, from: String) {
        binding.firstCurrency.text = from
        binding.secondCurrency.text = to
    }

    override fun clearFrom() {
        binding.secondEditText.applyWithDisabledTextWatcher(textWatcherTwo) {
            text = ""
        }
    }

    override fun clearTo() {
        binding.firstEditText.applyWithDisabledTextWatcher(textWatcherOne) {
            text = ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}






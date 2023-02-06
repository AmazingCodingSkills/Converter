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


// разметка, беру спинер, оттуда выпадает список со странами притом, я хотел туда положить ответ от сервера
// 3 элемента From, To, btn Convert realtime
// список со второго экрана
// хотел идти по пути абстрактного
// останавливаюсь на спинере

// Насколько хорошее решение ,рать спиннер? -> <оттом шит
// Ну;ны данные -> Возьмем у;е сущесту.щие из префов^ т7к загру;а.тся на старте
// UI переиспользовать нет осо,ого смысла Т7к он скорее всего изметися
// переиспользовать са,;ект и о,новлять на предыдущем 'кране
// При вводе суммы конвертации дол;ны отправлять запрос на текущий курс и его умно;ать и ото,ра;ать
// заугрузка текущего курса для вал.ты - ретрофит
// у;е загру;аем 'ти данные на мейн 'кране^ надо переиспользовать
// вот тут код работает, проблема в том, что мне нужно получить доступ к элементам, которые выше, а именно value
//25.01 что получается : работает часть функциональности, все работает через раз, остановился что поместил фунцкцию
// арифм в get api  затем были проблемы где вызывать, так как приходил нулл
// на текущий момент работает
class CalculatorFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding
    private var from = "USD"
    private var to = "EUR"
    private lateinit var textWatcherOne: TextWatcher
    private lateinit var textWatcherTwo: TextWatcher

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickSearchButtons()
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

                override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {

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

                override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {

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

    //сделал метод универсальным, путем вынесения аргументов, которые изменяются, теперь используя этот метод
    // в других местах могу просто передать нужные мне аргументы
    // еще один аргумент string в начале для понятности
    private fun loadCurrentRate(
        baseCurrencyCode: String,
        input: String,
        referenceCurrencyCode: String,
        editText: EditText,
        watcher: TextWatcher
    ) {
        val value = input.toDouble()
        CurrencyRatesRepository.getLatestApiResult(baseCurrencyCode) { rates ->
            val findItem = rates?.find { it.referenceCurrency.name == referenceCurrencyCode }
            val findValue = findItem?.referenceCurrency?.value ?: 0.0
            val result = (value * findValue).toString()
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
            loadCurrentRate(selectedCurrency, binding.etFirstConversion.text.toString(), to, binding.etSecondConversion, textWatcherTwo)
        }
        from = selectedCurrency
        binding.currencyFrom.text = selectedCurrency
    }

    private fun setDefaultValueTo(value: String) {
        if (!isEmptyInput()) {
            loadCurrentRate(value, binding.etSecondConversion.text.toString(), to, binding.etFirstConversion, textWatcherOne)
        }
        to = value
        binding.currencyTo.text = value
    }

    private fun isEmptyInput(): Boolean {
        val inputFrom = binding.etFirstConversion.text
        val inputTo = binding.etSecondConversion.text
        return inputFrom.isEmpty() || inputTo.isEmpty()
    }

    //Сложно. Надо вместо этого использовать форматтер
    fun String.removeAfter2Decimal(et: EditText) {
        return if (this.isEmpty() || this.isBlank() || this.toLowerCase() == "null") {
        } else {
            if (this.contains(".")) {
                var lastPartOfText = this.split(".")[this.split(".").size - 1]

                if (lastPartOfText.count() > 3) {
                    try {
                        lastPartOfText = this.substring(0, this.indexOf(".") + 4)
                        et.setText(lastPartOfText)
                        et.setSelection(lastPartOfText.length)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {

                }
            } else {

            }
        }
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






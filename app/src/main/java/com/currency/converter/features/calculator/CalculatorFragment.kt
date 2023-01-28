package com.currency.converter.features.calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
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
    private var isUsed: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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


        // val setValueEdit = Double.parseDouble(binding.etFirstConversion.text.toString()) не дает засетать значение по умолчанию
        // пробвал разные варианты
        //binding.etFirstConversion.text.toString().toDouble() = baseEditFrom
        // var x =  binding.etFirstConversion.text.toString().toDouble()
        // x = baseEditFrom


        firstEditText.addTextChangedListener {
            val text = firstEditText.text.toString()
            text.removeAfter2Decimal(firstEditText)
            if (secondEditText.isFocused()) {
                return@addTextChangedListener
            }
            if (text.isNotEmpty()) {
                val value = text.toDouble()
                loadCurrentRate(from, value, to, secondEditText)
            }
            if (text.isEmpty()) {
                secondEditText.text =
                    null // решаю проблему того, что если у меня пусто в первом editText, то должно быть пусто и во втором
            }
        }


        secondEditText.addTextChangedListener {

            val text = secondEditText.text.toString()
            text.removeAfter2Decimal(secondEditText)
            if (firstEditText.isFocused()) {
                return@addTextChangedListener
            }
            if (text.isNotEmpty()) {
                val value = text.toDouble()
                loadCurrentRate(to, value, from, firstEditText)
            }
            if (text.isEmpty()) {
                firstEditText.text = null
            }
        }


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
        value: Double,
        currencyCode: String,
        setResult: EditText
    ) {

        CurrencyRatesRepository.getLatestApiResult(baseCurrencyCode) { rates ->
            val findItem = rates?.find { it.referenceCurrency.name == currencyCode }
            val findValue = findItem?.referenceCurrency?.value ?: 0.0
            val result = (value * findValue).toString()
            setResult.setText(result)
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

    private fun setDefaultValueFrom(value: String) {
        from = value
        binding.currencyFrom.text = value
    }

    private fun setDefaultValueTo(value: String) {
        to = value
        binding.currencyTo.text = value
    }

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

    companion object {
        fun newInstance() = CalculatorFragment()
    }
}






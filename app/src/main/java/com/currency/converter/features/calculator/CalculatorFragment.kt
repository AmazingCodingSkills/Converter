package com.currency.converter.features.calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.currency.converter.ConverterApplication
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
    var baseCurrency = "USD"
    var convertedToCurrency = "EUR"
    var baseEdit = "0"

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
        binding.currencyFrom.text = baseCurrency
        binding.currencyTo.text = convertedToCurrency
       binding.etFirstConversion.setText(baseEdit)
        binding.etSecondConversion.setText(baseEdit)
        // val setValueEdit = Double.parseDouble(binding.etFirstConversion.text.toString()) не дает засетать значение по умолчанию
        // пробвал разные варианты
        // binding.etFirstConversion.text.toString().toDouble() = baseEditFrom
        // var x =  binding.etFirstConversion.text.toString().toDouble()
        // x = baseEditFrom

        binding.etFirstConversion.addTextChangedListener {
            val itemFrom = ConverterApplication.PreferencesManager.get<String>("QWERTY")
            if (itemFrom == baseCurrency) {
                CurrencyRatesRepository.getLatestApiResult(itemFrom) { rates ->
                    val findItemFrom =
                        rates?.find { it.referenceCurrency.name == convertedToCurrency }
                    val baseEditFrom = findItemFrom?.referenceCurrency?.value
                    val resultFrom = ((binding.etFirstConversion.text.toString()
                        .toDouble()) * baseEditFrom!!).toString()
                    binding.etSecondConversion.setText(resultFrom)
                    Log.d("baseEdit", "$baseEditFrom")
                }
            }

            if (itemFrom != null && itemFrom != baseCurrency) {
                CurrencyRatesRepository.getLatestApiResult(itemFrom) { rates ->
                    val findItemFrom =
                        rates?.find { it.referenceCurrency.name == convertedToCurrency }
                    val valueCurrencyTo = findItemFrom?.referenceCurrency?.value
                    ConverterApplication.PreferencesManager.put(valueCurrencyTo, "ZZZ")
                    val resultTo = ((binding.etFirstConversion.text.toString()
                        .toDouble()) * valueCurrencyTo!!).toString()
                    binding.etSecondConversion.setText(resultTo)
                }
            }
        }

        binding.etSecondConversion.addTextChangedListener {
            val itemTo = ConverterApplication.PreferencesManager.get<String>("QWERTY1")
            if (itemTo == convertedToCurrency) {
                CurrencyRatesRepository.getLatestApiResult(itemTo) { rates ->
                    val findItemTo =
                        rates?.find { it.referenceCurrency.name == baseCurrency }
                    val baseEditTo = findItemTo?.referenceCurrency?.value
                    val resultTo = ((binding.etSecondConversion.text.toString()
                        .toDouble()) * baseEditTo!!).toString()
                    binding.etFirstConversion.setText(resultTo)
                    Log.d("baseEdit", "$baseEditTo")
                }
            }
            if (itemTo != null && itemTo != convertedToCurrency) {
                CurrencyRatesRepository.getLatestApiResult(itemTo) { rates ->
                    val findItemTo =
                        rates?.find { it.referenceCurrency.name == baseCurrency }
                    val valueCurrencyTo = findItemTo?.referenceCurrency?.value
                    ConverterApplication.PreferencesManager.put(valueCurrencyTo, "ZZZ")
                    val resultTo = ((binding.etSecondConversion.text.toString()
                        .toDouble()) * valueCurrencyTo!!).toString()
                    binding.etFirstConversion.setText(resultTo)
                }
            }
        }

        setFragmentResultListener("CURRENCY_KEY") { requestKey, bundle ->
            Log.d("TAG", "Result = $requestKey, $bundle")
            val tag = bundle.getString("TAG")
            val item = bundle.getParcelable<CurrencyItem>("SELECTED_CURRENCY")!!

            if (tag == "FROM") {
                setDefaultValueFrom(item.id)
                CurrencyRatesRepository.getLatestApiResult(item.id) { rates ->
                    val findItemFrom = rates?.find { it.referenceCurrency.name == item.id }
                    val itemFrom = findItemFrom?.referenceCurrency?.name
                    ConverterApplication.PreferencesManager.put(
                        itemFrom,
                        "QWERTY"
                    ) // вынести константы

                }
            }
            if (tag == "TO") {
                setDefaultValueTo(item.id)
                CurrencyRatesRepository.getLatestApiResult(item.id) { rates ->
                    val findItemTo = rates?.find { it.referenceCurrency.name == item.id }
                    val itemTo = findItemTo?.baseCurrencyName
                    ConverterApplication.PreferencesManager.put(itemTo, "QWERTY1")
                }
            }
        }

    }


    fun clickSearchButtons() = with(binding) {

        searchCurrencyFrom.setOnClickListener {
            val allCurrencyBottomSheet = AllCurrencyBottomSheet()
            allCurrencyBottomSheet.show(childFragmentManager, "FROM")

        }

        searchCurrencyTo.setOnClickListener {
            val allCurrencyBottomSheet = AllCurrencyBottomSheet()
            allCurrencyBottomSheet.show(childFragmentManager, "TO")


        }
    }

    private fun setDefaultValueFrom(text: String) {
        binding.currencyFrom.setText(text)
    }

    private fun setDefaultValueTo(value: String) {
        binding.currencyTo.setText(value)
    }
    override fun onDestroy() {
        super.onDestroy()
        ConverterApplication.PreferencesManager.put(baseCurrency, "QWERTY")
        ConverterApplication.PreferencesManager.put(convertedToCurrency, "QWERTY1")
        binding.etFirstConversion.setText(baseEdit)
        binding.etSecondConversion.setText(baseEdit)
    }

    companion object {
        fun newInstance() = CalculatorFragment()
    }
}





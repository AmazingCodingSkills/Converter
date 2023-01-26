package com.currency.converter.features.calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    var baseEditFrom = 1.0


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
        binding.etFirstConversion.text.toString()

        binding.etFirstConversion.addTextChangedListener {

            val itemFrom = ConverterApplication.PreferencesManager.get<String>("QWERTY")

            if (itemFrom == null) {
                CurrencyRatesRepository.getLatestApiResult(baseCurrency) { rates ->
                    val findItemFrom =
                        rates?.find { it.referenceCurrency.name == convertedToCurrency }
                    val baseEditFrom = findItemFrom?.referenceCurrency?.value
                    ConverterApplication.PreferencesManager.put(baseEditFrom, "XXX")
                    Log.d("baseEdit", "$baseEditFrom")
                    //val x =  binding.etFirstConversion.toString().toDouble() *  1.0 //z.toString().toDouble()


                    // decimalFormatter

                }

            }
            getApiResult()
            if (itemFrom != null) {
                CurrencyRatesRepository.getLatestApiResult(itemFrom) { rates ->
                    val findItemFrom =
                        rates?.find { it.referenceCurrency.name == convertedToCurrency }
                    val valueCurrencyTo = findItemFrom?.referenceCurrency?.value
                    binding.etSecondConversion.text
                    // decimalFormatter
                }

            }

        }

        binding.etSecondConversion.addTextChangedListener {
            val itemTo = ConverterApplication.PreferencesManager.get<String>("QWERTY1")
            if (itemTo != null) {
                CurrencyRatesRepository.getLatestApiResult(itemTo) { rates ->
                    val filteredRates =
                        rates?.filter { it.referenceCurrency.name == baseCurrency }
                    Log.d("Hole3", "$filteredRates")

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
                    val itemFrom = findItemFrom?.baseCurrencyName
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

    private fun getApiResult() = with(binding) {

        if (etFirstConversion.text.isNotEmpty() && etFirstConversion.text.isNotBlank()) {
            val getValue = ConverterApplication.PreferencesManager.get<Double?>("XXX")
            val result = ((binding.etFirstConversion.text.toString()
                .toDouble()) * getValue!!).toString()
            binding.etSecondConversion.setText(result)
            Log.d("ace", "$getValue")
            if (baseCurrency == convertedToCurrency) {
                Toast.makeText(
                    context,
                    "Please pick a currency to convert",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        ConverterApplication.PreferencesManager.sp.edit().remove("QWERTY").apply()
    }

    companion object {
        fun newInstance() = CalculatorFragment()
    }
}





package com.currency.converter.features.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.example.converter.databinding.FragmentTabLayoutFavoritesAllBinding


class CurrenciesFragment : Fragment() {
    private lateinit var binding: FragmentTabLayoutFavoritesAllBinding
    private lateinit var adapterCurrenciesUse: CurrenciesAdapter
    private lateinit var allCurrency: List<CurrencyItem>
    private lateinit var saveCurrency: List<CurrencyItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabLayoutFavoritesAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerFavoriteAll.apply {
            layoutManager = LinearLayoutManager(activity)
            adapterCurrenciesUse = CurrenciesAdapter { item ->
                updateFavorite(item)
            }

            binding.recyclerFavoriteAll.adapter = adapterCurrenciesUse
            this.itemAnimator =
                null // это позволяет в Recycler View убрать анимацию клика одного контейнера
        }
        allCurrency =
            ConverterApplication.ModelPreferencesManager.get<List<CurrencyItem>>("KEY_TWO")
                .orEmpty()
        Log.d("qwerty", "${allCurrency::class.java}")
        adapterCurrenciesUse.submitList(allCurrency)

    }

    private fun updateFavorite(updatedItem: CurrencyItem) {
        val items =
            adapterCurrenciesUse.currentList // [0]..[178], [0] - currencyItem1, [1] - currencyItem2
        val updatedElementIndex =
            items.indexOf(updatedItem) // индекс для конкретного элемента, который сейчас выбран

        //делаем избранным
        // CurrencyItem("RUB", false) - before
        // CurrentItem("RUB",true) - after
        val newItem = updatedItem.copy(isFavorite = updatedItem.isFavorite.not())

        //создаем новый список с измененным элементом
        val selectCurrencies = items.toMutableList().also { list ->
            list[updatedElementIndex] =
                newItem // если я тут указываю как ты писал вместо list-> list[] -> it[] разницы нет??
        }
        ConverterApplication.ModelPreferencesManager.put(selectCurrencies, "KEY_TWO")
        saveCurrency =
            ConverterApplication.ModelPreferencesManager.get<List<CurrencyItem>>("KEY_TWO").orEmpty()
        //обновляем в шаредах чтобы работало между перезапусками приложения
        //....
        Log.d("qwerty", "${saveCurrency::class.java}")
        //обновляем на UI новым списком
        val result = allCurrency.toMutableList()
        for (item in saveCurrency) {
            val index = allCurrency.indexOfFirst { it.currencyName == item.currencyName }
            if (index < 0) {
                result.add(item)
            } else {
                result[index] = item
            }
        }
        adapterCurrenciesUse.submitList(result)

    }

    companion object {
        fun newInstance() = CurrenciesFragment()
    }

}
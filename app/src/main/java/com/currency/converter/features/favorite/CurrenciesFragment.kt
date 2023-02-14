package com.currency.converter.features.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.ConverterApplication.PreferencesManager.SELECT_KEY
import com.example.converter.databinding.FragmentTabLayoutFavoritesAllBinding


class CurrenciesFragment : Fragment() {

    private lateinit var binding: FragmentTabLayoutFavoritesAllBinding
    private lateinit var adapterCurrencies: CurrenciesAdapter
    private lateinit var allCurrency: List<CurrencyItem>
    private lateinit var selectFavoriteCurrency: List<CurrencyItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabLayoutFavoritesAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerListCurrency.apply {
            layoutManager = LinearLayoutManager(activity)
            adapterCurrencies = CurrenciesAdapter(onItemClickListener = { item ->
                updateFavorite(item)
            })
            binding.recyclerListCurrency.adapter = adapterCurrencies
            this.itemAnimator =
                null // это позволяет в Recycler View убрать анимацию клика одного контейнера
        }
    }

    override fun onResume() {
        super.onResume()
        allCurrency =
            ConverterApplication.PreferencesManager.get<List<CurrencyItem>>(
                ConverterApplication.PreferencesManager.ALL_CURRENCY_KEY
            ).orEmpty()
        selectFavoriteCurrency =
            ConverterApplication.PreferencesManager.get<List<CurrencyItem>>(SELECT_KEY)
                .orEmpty()
        val chooseFavoriteItem = allCurrency.toMutableList()
        for (item in selectFavoriteCurrency) {
            val index = allCurrency.indexOfFirst { it.currencyName == item.currencyName }
            if (index < 0) {
                chooseFavoriteItem.add(item)
            } else {
                chooseFavoriteItem[index] = item
            }
        }
        adapterCurrencies.submitList(chooseFavoriteItem)
    }

    private fun updateFavorite(updatedItem: CurrencyItem) {
        val currentAllCurrencyItem =
            adapterCurrencies.currentList // [0]..[178], [0] - currencyItem1, [1] - currencyItem2
        val updatedElementIndex =
            currentAllCurrencyItem.indexOf(updatedItem) // индекс для конкретного элемента, который сейчас выбран
        //делаем избранным
        // CurrencyItem("RUB", false) - before
        // CurrentItem("RUB",true) - after
        val newAllCurrencyItem = updatedItem.copy(
            isFavorite = updatedItem.isFavorite.not()
        )
        //создаем новый список с измененным элементом
        val favoriteAllCurrencies = currentAllCurrencyItem.toMutableList().also { currencies ->
            currencies[updatedElementIndex] =
                newAllCurrencyItem
        }
        ConverterApplication.PreferencesManager.put(favoriteAllCurrencies, SELECT_KEY)
        Log.d("qwerty", "${favoriteAllCurrencies}")
        //обновляем на UI новым списком
        adapterCurrencies.submitList(favoriteAllCurrencies)
    }

    companion object {
        fun newInstance() = CurrenciesFragment()
    }
}
package com.example.rate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.converter.core.favoritemodel.CurrencyItem
import com.converter.core.ConverterApplication.Companion.appComponent
import com.converter.core.room.Favorite
import com.example.rate.databinding.FragmentTabLayoutFavoritesAllBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CurrenciesFragment() : Fragment() {

    private lateinit var binding: FragmentTabLayoutFavoritesAllBinding
    private lateinit var adapterCurrencies: CurrenciesAdapter

    private val component = appComponent.provideFavouriteCurrencyRepository()

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
            this.itemAnimator = null
        }
    }

    override fun onResume() {
        super.onResume()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val favoriteCurrencyItems = component.getAll().map {
                CurrencyItem(it.id, it.currencyName, it.isFavorite)
            }
            withContext(Dispatchers.Main) {
                adapterCurrencies.submitList(favoriteCurrencyItems)
            }
        }
    }


    private fun updateFavorite(item: CurrencyItem) {
        val currentAllCurrencyItem =
            adapterCurrencies.currentList
        val updatedElementIndex =
            currentAllCurrencyItem.indexOf(item)

        val newAllCurrencyItem = item.copy(
            isFavorite = item.isFavorite.not()
        )

        val favoriteAllCurrencies = currentAllCurrencyItem.toMutableList().also { currencies ->
            currencies[updatedElementIndex] =
                newAllCurrencyItem
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val listFavourite =
                favoriteAllCurrencies.map { Favorite(it.id, it.currencyName, it.isFavorite) }
            val favoriteItem = listFavourite[updatedElementIndex]
            component.update(favoriteItem.id, favoriteItem.isFavorite)
            withContext(Dispatchers.Main) {
                adapterCurrencies.submitList(favoriteAllCurrencies)
            }
        }
    }

    companion object {
        fun newInstance() = CurrenciesFragment()
    }
}
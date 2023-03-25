package com.currency.converter.features.favorite

import CurrenciesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.converter.core.currency.CurrencyItem
import com.example.converter.databinding.FragmentTabLayoutFavoritesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OnlyFavoritesFragment : Fragment() {

    private lateinit var binding: FragmentTabLayoutFavoritesBinding
    private lateinit var adapterSelectedFavorite: CurrenciesAdapter


    private val component = ConverterApplication.appComponent.provideFavouriteCurrencyRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabLayoutFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectCurrencyRV.apply {
            layoutManager = LinearLayoutManager(activity)
            adapterSelectedFavorite = CurrenciesAdapter { item -> removeFavorite(item) }
            binding.selectCurrencyRV.adapter = adapterSelectedFavorite
            this.itemAnimator = null
        }

    }

    override fun onResume() {
        super.onResume()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val listFavourite = component.getFavoriteItems()
            withContext(Dispatchers.Main) {
                val listFavouriteForUI =
                    listFavourite.map { CurrencyItem(it.id, it.currencyName, it.isFavorite) }
                adapterSelectedFavorite.submitList(listFavouriteForUI)
                displayEmpty(listFavouriteForUI)
            }
        }
    }

    private fun displayEmpty(value: List<CurrencyItem>) {
        if (value.isNotEmpty()) {
            binding.selectCurrencyRV.visibility = View.VISIBLE
            binding.hintEmptyScreen.visibility = View.GONE
        } else {
            binding.selectCurrencyRV.visibility = View.GONE
            binding.hintEmptyScreen.visibility = View.VISIBLE
        }
    }

    private fun removeFavorite(item: CurrencyItem) {

        val removeItemFromAllList =
            adapterSelectedFavorite.currentList.toMutableList()
        val removeElementIndex =
            removeItemFromAllList.indexOf(item)
        removeItemFromAllList.removeAt(removeElementIndex)
        adapterSelectedFavorite.submitList(removeItemFromAllList)
        displayEmpty(removeItemFromAllList)
        updateIsFavorite(item.id, !item.isFavorite)
    }

    private fun updateIsFavorite(id: String, isFavorite: Boolean) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            component.update(id, isFavorite)
        }
    }

    companion object {
        fun newInstance() = OnlyFavoritesFragment()
    }
}

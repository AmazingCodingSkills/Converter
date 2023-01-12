package com.currency.converter.features.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.ConverterApplication.PreferencesManager.FAVORITE_CURRENCIES_KEY
import com.example.converter.databinding.FragmentTabLayoutFavoritesAllBinding


class OnlyFavoritesFragment : Fragment() {

    private lateinit var binding: FragmentTabLayoutFavoritesAllBinding
    private lateinit var adapterSelectedFavorite: CurrenciesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabLayoutFavoritesAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerFavoriteItem.apply {
            layoutManager = LinearLayoutManager(activity)
            adapterSelectedFavorite = CurrenciesAdapter { item -> removeFavorite(item) }
            binding.recyclerFavoriteItem.adapter = adapterSelectedFavorite
            this.itemAnimator = null
        }
    }

    override fun onResume() {
        super.onResume()
        val favoriteItemFromSP =
            ConverterApplication.PreferencesManager.get<List<CurrencyItem>>(
                ConverterApplication.PreferencesManager.SELECT_KEY
            ).orEmpty()

        val onlySelectedFavoriteItem = favoriteItemFromSP.filter { it.isFavorite }
        ConverterApplication.PreferencesManager.put(onlySelectedFavoriteItem,FAVORITE_CURRENCIES_KEY)
        adapterSelectedFavorite.submitList(onlySelectedFavoriteItem)
    }

    private fun removeFavorite(removeItem: CurrencyItem) {
        val removeItemFromAllList =
            adapterSelectedFavorite.currentList.toMutableList()
        val removeElementIndex =
            removeItemFromAllList.indexOf(removeItem)
        removeItemFromAllList.removeAt(removeElementIndex)
        ConverterApplication.PreferencesManager.put(
            removeItemFromAllList,
            ConverterApplication.PreferencesManager.SELECT_KEY
        )
        adapterSelectedFavorite.submitList(removeItemFromAllList)
    }

    companion object {
        fun newInstance() = OnlyFavoritesFragment()
    }
}

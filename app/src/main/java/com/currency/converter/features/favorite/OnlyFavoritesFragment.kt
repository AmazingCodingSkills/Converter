package com.currency.converter.features.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.currency.converter.ConverterApplication.PreferencesManager.FAVORITE_CURRENCIES_KEY
import com.example.converter.databinding.FragmentTabLayoutFavoritesBinding


class OnlyFavoritesFragment : Fragment() {

    private lateinit var binding: FragmentTabLayoutFavoritesBinding
    private lateinit var adapterSelectedFavorite: CurrenciesAdapter

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
        val favoriteItemFromSP =
            ConverterApplication.PreferencesManager.get<List<CurrencyItem>>(
                ConverterApplication.PreferencesManager.SELECT_KEY
            ).orEmpty()

        val onlySelectedFavoriteItem = favoriteItemFromSP.filter { it.isFavorite }
        ConverterApplication.PreferencesManager.put(onlySelectedFavoriteItem,FAVORITE_CURRENCIES_KEY)
        adapterSelectedFavorite.submitList(onlySelectedFavoriteItem)

           displayEmpty(onlySelectedFavoriteItem)
    }

private fun displayEmpty(value: List<CurrencyItem>){
    if (value.isNotEmpty()) {
        binding.selectCurrencyRV.visibility = View.VISIBLE
        binding.hintEmpty.visibility = View.GONE
    } else {
        binding.selectCurrencyRV.visibility = View.GONE
        binding.hintEmpty.visibility = View.VISIBLE
    }
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
        displayEmpty(removeItemFromAllList)
    }

    companion object {
        fun newInstance() = OnlyFavoritesFragment()
    }
}

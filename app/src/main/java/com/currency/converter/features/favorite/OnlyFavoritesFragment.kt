package com.currency.converter.features.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication
import com.example.converter.databinding.FragmentTabLayoutFavoritesAllBinding


class OnlyFavoritesFragment : Fragment() {
    private lateinit var binding: FragmentTabLayoutFavoritesAllBinding
    private lateinit var adapterSelected: CurrenciesAdapter


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
            adapterSelected = CurrenciesAdapter{ item -> removeFavorite(item)}
            binding.recyclerFavoriteAll.adapter = adapterSelected
            this.itemAnimator = null
        }

    }

    override fun onResume() {
        super.onResume()
        val favoriteItemFromSP =
            ConverterApplication.ModelPreferencesManager.get<List<CurrencyItem>>(
                ConverterApplication.ModelPreferencesManager.FAVORITE_KEY
            ).orEmpty()

        val onlySelectedItemFromSP = favoriteItemFromSP.filter { it.isFavorite }
        adapterSelected.submitList(onlySelectedItemFromSP)
    }

   private fun removeFavorite(removeItem: CurrencyItem) {
        val removeItemList =
            adapterSelected.currentList.toMutableList()
        val removeElementIndex =
            removeItemList.indexOf(removeItem)
       removeItemList.removeAt(removeElementIndex)
       ConverterApplication.ModelPreferencesManager.put(removeItemList,
           ConverterApplication.ModelPreferencesManager.FAVORITE_KEY
       )
       adapterSelected.submitList(removeItemList)
    }

    companion object {
        fun newInstance() = OnlyFavoritesFragment()
    }
}
// test username

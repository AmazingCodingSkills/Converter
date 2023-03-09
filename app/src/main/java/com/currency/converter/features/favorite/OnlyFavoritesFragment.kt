package com.currency.converter.features.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication.Companion.appComponent
import com.currency.converter.base.favoritemodel.CurrencyItem
import com.example.converter.databinding.FragmentTabLayoutFavoritesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OnlyFavoritesFragment : Fragment() {

    private lateinit var binding: FragmentTabLayoutFavoritesBinding
    private lateinit var adapterSelectedFavorite: CurrenciesAdapter

    /*private val component: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(application)
    } // лучше привести к такому виду? и на остальном втором фрагменте?
    // просто я думаю, что это получается второе создание?
    // раз это не отдельный компонент
*/

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
            val favoriteItemFrom = appComponent.providesRoom().getCurrencyItem()
            withContext(Dispatchers.Main) {
                val onlySelectedFavoriteItem = favoriteItemFrom.filter { it.isFavorite }
                adapterSelectedFavorite.submitList(onlySelectedFavoriteItem)
                displayEmpty(onlySelectedFavoriteItem)
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

    private fun removeFavorite(removeItem: CurrencyItem) {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val removeItemFromAllList =
                    adapterSelectedFavorite.currentList.toMutableList()
                removeItemFromAllList.remove(removeItem)
                adapterSelectedFavorite.submitList(removeItemFromAllList)
                displayEmpty(removeItemFromAllList)
            }
            appComponent.providesRoom().updateCurrencyItem(removeItem)
        }
    }

    companion object {
        fun newInstance() = OnlyFavoritesFragment()
    }
}

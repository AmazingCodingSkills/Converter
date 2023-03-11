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
import com.currency.converter.base.favoritemodel.CurrencyItem
import com.example.converter.databinding.FragmentTabLayoutFavoritesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class OnlyFavoritesFragment : Fragment() {

    private lateinit var binding: FragmentTabLayoutFavoritesBinding
    private lateinit var adapterSelectedFavorite: CurrenciesAdapter

    private lateinit var favoriteRepository: FavoriteRepository

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
        favoriteRepository = FavoriteRepository(
            ConverterApplication.appComponent.providesRoom()
        )
        lifecycleScope.launch {
            favoriteRepository.favorites.collectLatest { favorites ->
                adapterSelectedFavorite.submitList(favorites.map { CurrencyItem(it.id, it.currencyName, it.isFavorite) })
            }
        }

        /*viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
           *//* val myEntity = appComponent.providesRoom().getAll()
            myEntity.isFavorite = true*//*
            val favoriteItemFrom = appComponent.providesRoom().getItemsByUpdate(true)
            withContext(Dispatchers.Main) {
                //val onlySelectedFavoriteItem = favoriteItemFrom.filter { it.isFavorite }
                val x = favoriteItemFrom.map { CurrencyItem(it.id, it.currencyName, it.isFavorite) }
                adapterSelectedFavorite.submitList(x)
                displayEmpty(x)
            }
        }*/
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

    /*private fun updateIsFavorite(id: String, isFavorite: Boolean) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
            val entity = appComponent.providesRoom().getById(id)
            entity.isFavorite = isFavorite
            appComponent.providesRoom().update(entity)
        }
    }*/




    companion object {
        fun newInstance() = OnlyFavoritesFragment()
    }
}
/*appComponent.providesRoom().getItemsByUpdate(true).map { CurrencyItem(it.id,it.currencyName,it.isFavorite) }.toMutableList()*/
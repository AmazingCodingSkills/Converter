package com.currency.converter.features.favorite

import CurrenciesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.converter.ConverterApplication.Companion.appComponent
import com.currency.converter.base.favoritemodel.CurrencyItem
import com.example.converter.databinding.FragmentTabLayoutFavoritesAllBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CurrenciesFragment() : Fragment() {

    private lateinit var binding: FragmentTabLayoutFavoritesAllBinding
    private lateinit var adapterCurrencies: CurrenciesAdapter

    private lateinit var favoriteRepository: FavoriteRepository

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
        // замените это на желаемое значение isFavorite
        favoriteRepository = FavoriteRepository(
            appComponent.providesRoom()
        )
        lifecycleScope.launch {
            favoriteRepository.favorites.collectLatest { favorites ->
                adapterCurrencies.submitList(favorites.map { CurrencyItem(it.id, it.currencyName, it.isFavorite) })
            }
        }
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            /* val id =
                 appComponent.providesRoom().getAll().firstOrNull()?.id ?: ""// замените это на реальный ID вашей сущности
             val isFavorite = false
             updateIsFavoriteForAll(isFavorite)*/
            val favoriteCurrencyItems = appComponent.providesRoom().getAll().map {
                CurrencyItem(it.id, it.currencyName, it.isFavorite)
            }
         //  appComponent.providesRoom().getCurrencyItem()
            withContext(Dispatchers.Main) {
                adapterCurrencies.submitList(favoriteCurrencyItems)
            }
        }
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

        adapterCurrencies.submitList(favoriteAllCurrencies)
       /* viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val x = favoriteAllCurrencies.map { Favorite(it.id, it.currencyName, it.isFavorite) }
            appComponent.providesRoom().insertAll(x)
            withContext(Dispatchers.Main) {
                adapterCurrencies.submitList(favoriteAllCurrencies)
            }
        }*/
    }

    /*   private fun updateIsFavorite(id: String, isFavorite: Boolean) {
           viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
               val entity = appComponent.providesRoom().getById(id)
               entity.isFavorite = isFavorite
               appComponent.providesRoom().update(entity)
           }
       }

       private fun updateIsFavoriteForAll(isFavorite: Boolean) {
           viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
               val dao = appComponent.providesRoom()
               val currencyItems = dao.getAll()
               for (currencyItem in currencyItems) {
                   currencyItem.isFavorite = false
                   dao.update(currencyItem)
               }
           }
       }*/

    companion object {
        fun newInstance() = CurrenciesFragment()
    }
}
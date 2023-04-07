package com.example.rate.presentation.favourite.allcurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.converter.core.currency.domain.CurrencyItem
import com.converter.core.favorite.domain.FavouriteCurrencyRepository
import com.converter.core.favorite.data.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class CurrenciesViewModel @Inject constructor(
    private val favouriteCurrencyRepository: FavouriteCurrencyRepository
) :
    ViewModel() {

    private val viewState = MutableStateFlow<CurrenciesViewState>(CurrenciesViewState.Update)
    val state: StateFlow<CurrenciesViewState> = viewState.asStateFlow()


    /*init {
        favouriteCurrencyRepository.onUpdateItem().onEach {
            updateFavourite(it)
        }.launchIn(viewModelScope)
    }*/


    fun handleAction(action: CurrenciesViewAction, item: CurrencyItem) {
        when (action) {
            CurrenciesViewAction.UpdateList -> updateFavourite(item)
        }
    }

    private fun updateFavourite(item: CurrencyItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteCurrencyItems = favouriteCurrencyRepository.getAll().map {
                CurrencyItem(it.id, it.currencyName, it.isFavorite)
            }
            val updatedElementIndex =
                favoriteCurrencyItems.indexOf(item)
            val newAllCurrencyItem = item.copy(
                isFavorite = item.isFavorite.not()
            )

            val favoriteAllCurrencies = favoriteCurrencyItems.toMutableList().also { currencies ->
                currencies[updatedElementIndex] =
                    newAllCurrencyItem
            }
            val listFavourite =
                favoriteAllCurrencies.map { Favorite(it.id, it.currencyName, it.isFavorite) }
            val favoriteItem = listFavourite[updatedElementIndex]
            favouriteCurrencyRepository.update(favoriteItem.id, favoriteItem.isFavorite)
            viewState.value = CurrenciesViewState.Content(favoriteAllCurrencies)
        }
    }

    fun updateScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteCurrencyItems = favouriteCurrencyRepository.getAll().map {
                CurrencyItem(it.id, it.currencyName, it.isFavorite)
            }
            viewState.value = CurrenciesViewState.Content(favoriteCurrencyItems)
        }
    }
}


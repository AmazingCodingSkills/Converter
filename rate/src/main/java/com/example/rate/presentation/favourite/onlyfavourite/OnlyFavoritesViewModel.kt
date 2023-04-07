package com.example.rate.presentation.favourite.onlyfavourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.converter.core.currency.domain.CurrencyItem
import com.converter.core.favorite.domain.FavouriteCurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class OnlyFavoritesViewModel @Inject constructor(
    private val favouriteCurrencyRepository: FavouriteCurrencyRepository
) :
    ViewModel() {

    private val viewState =
        MutableStateFlow<OnlyFavouritesViewState>(OnlyFavouritesViewState.Remove)
    val state: StateFlow<OnlyFavouritesViewState> = viewState.asStateFlow()


    /*init {
        favouriteCurrencyRepository.onUpdateItem().onEach {
            removeFavourite(it)
        }.launchIn(viewModelScope)
    }*/

    fun handleAction(action: OnlyFavouritesViewAction, item: CurrencyItem) {
        when (action) {
            OnlyFavouritesViewAction.RemoveItem -> removeFavourite(item)
        }
    }

    private fun removeFavourite(item: CurrencyItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val removeItemFromAllList = favouriteCurrencyRepository.getFavoriteItems().map {
                CurrencyItem(it.id, it.currencyName, it.isFavorite)
            }.toMutableList()
            val removeElementIndex =
                removeItemFromAllList.indexOf(item)
            removeItemFromAllList.removeAt(removeElementIndex)
            if (removeItemFromAllList.isNotEmpty()) {
                viewState.value = OnlyFavouritesViewState.Content(removeItemFromAllList)
            } else {
                viewState.tryEmit(OnlyFavouritesViewState.Empty)
            }
            favouriteCurrencyRepository.update(item.id, !item.isFavorite)
        }
    }

    fun reloadScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            val listFavourite = favouriteCurrencyRepository.getFavoriteItems()
            val listFavouriteForUI =
                listFavourite.map { CurrencyItem(it.id, it.currencyName, it.isFavorite) }
            viewState.value = OnlyFavouritesViewState.Content(listFavouriteForUI)
            if (listFavouriteForUI.isEmpty()) {
                viewState.tryEmit(OnlyFavouritesViewState.Empty)
            }
        }
    }
}



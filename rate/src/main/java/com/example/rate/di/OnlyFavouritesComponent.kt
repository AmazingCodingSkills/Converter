package com.example.rate.di

import com.converter.core.favorite.domain.FavouriteCurrencyRepository
import com.converter.core.di.AppComponent
import com.converter.core.di.FragmentScope
import com.example.rate.presentation.favourite.onlyfavourite.OnlyFavouritesViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class OnlyFavouritesModule {

    @Provides
    fun onlyFavouritesViewModelFactory(favouriteCurrencyRepository: FavouriteCurrencyRepository): OnlyFavouritesViewModelFactory =
        OnlyFavouritesViewModelFactory(favouriteCurrencyRepository)

}


@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [OnlyFavouritesModule::class])
interface OnlyFavouritesComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): OnlyFavouritesComponent
    }

    fun onlyFavouritesViewModelFactory(): OnlyFavouritesViewModelFactory

    fun provideFavouriteCurrencyRepository(): FavouriteCurrencyRepository

}
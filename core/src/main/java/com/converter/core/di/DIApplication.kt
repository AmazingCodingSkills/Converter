package com.converter.core

import android.app.Application
import androidx.room.Room
import com.converter.core.ConverterApplication.Companion.application
import com.converter.core.currency.CurrencyRatesRepository
import com.converter.core.currency.CurrencyRatesRepositoryImpl
import com.converter.core.currency.CurrencyService
import com.converter.core.favouritecurrency.FavouriteCurrencyRepository
import com.converter.core.favouritecurrency.FavouriteCurrencyRepositoryImpl
import com.converter.core.network.NetworkRepository
import com.converter.core.network.NetworkRepositoryImpl
import com.converter.core.room.AppDatabase
import com.converter.core.room.CurrencyItemDao
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Scope
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun networkRepository(): NetworkRepository

    fun currencyRatesRepository(): CurrencyRatesRepository

    fun providesCurrencyService(): CurrencyService

    fun providesRoom(): CurrencyItemDao

    fun provideFavouriteCurrencyRepository(): FavouriteCurrencyRepository

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}

@Module(includes = [RoomModule::class, RetrofitModule::class])
object AppModule {

    @Provides
    @Singleton
    fun providesNetworkRepositoryImpl(application: Application): NetworkRepository {
        return NetworkRepositoryImpl(application)
    }

    @Provides
    @Singleton
    fun provideCurrencyRatesRepository(currencyService: CurrencyService): CurrencyRatesRepository {
        return CurrencyRatesRepositoryImpl(currencyService)
    }

    @Provides
    @Singleton
    fun provideFavouriteCurrencyRepository(currencyItemDao: CurrencyItemDao): FavouriteCurrencyRepository {
        return FavouriteCurrencyRepositoryImpl(currencyItemDao)
    }

}

@Module
class RoomModule() {
    @Provides
    @Singleton
    fun providesRoom(): CurrencyItemDao {
        val db = Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java, "Favorite"
        ).build()
        return db.currencyItemDao()
    }

}

@Module
class RetrofitModule() {

    @Provides
    @Singleton
    fun providesCurrencyService(): CurrencyService {
        val retrofit = Retrofit.Builder().baseUrl("https://api.currencyscoop.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            ).build()
        return retrofit.create(CurrencyService::class.java)
    }
}

@Scope
annotation class FragmentScope



package com.currency.converter

import android.app.Application
import androidx.room.Room
import com.currency.converter.ConverterApplication.Companion.application
import com.currency.converter.base.currency.CurrencyRatesRepository
import com.currency.converter.base.currency.CurrencyRatesRepositoryImpl
import com.currency.converter.base.currency.CurrencyService
import com.currency.converter.base.network.NetworkRepository
import com.currency.converter.base.network.NetworkRepositoryImpl
import com.currency.converter.base.room.AppDatabase
import com.currency.converter.base.room.CurrencyItemDao
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

// AppCompoent (deps) -> Rates (deps) | Calculator (deps)

// minunes
// - build time
// - links
// 1 file


// AppComponent (deps) |  AppCompoent -> Rates(deps)  | AppCompoent -> Calculator (deps)

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun networkRepository(): NetworkRepository

    fun currencyRatesRepository(): CurrencyRatesRepository

    fun providesCurrencyService(): CurrencyService

    fun providesRoom(): CurrencyItemDao


    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}

@Module(includes = [RoomModule::class, RoomModule.RetrofitModule::class])
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
}

@Module
class RoomModule() {
    @Provides
    @Singleton
    fun providesRoom(): CurrencyItemDao {
        val db = Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java, "CurrencyItem"
        ).build()
        return db.currencyItemDao()
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
}

@Scope
annotation class FragmentScope



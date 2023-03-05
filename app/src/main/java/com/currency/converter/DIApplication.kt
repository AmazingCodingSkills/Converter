package com.currency.converter

import android.app.Application
import com.currency.converter.base.currency.CurrencyRatesRepository
import com.currency.converter.base.currency.CurrencyRatesRepositoryImpl
import com.currency.converter.base.network.NetworkRepository
import com.currency.converter.base.network.NetworkRepositoryImpl
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
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

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}

@Module
object AppModule {

    @Provides
    @Singleton
    fun providesNetworkRepositoryImpl(application: Application): NetworkRepository {
        return NetworkRepositoryImpl(application)
    }

    @Provides
    @Singleton
    fun provideCurrencyRatesRepository(): CurrencyRatesRepository {
        return CurrencyRatesRepositoryImpl()
    }

}

@Scope
annotation class FragmentScope



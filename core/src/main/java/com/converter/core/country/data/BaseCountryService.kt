package com.converter.core.country.data


import android.content.Context
import com.converter.core.R


object BaseCountryService {
    fun countryList(context: Context): List<CountryModel> {
        return listOf(
            CountryModel(context.getString(R.string.russia), "RUB",R.drawable.russia),
            CountryModel(context.getString(R.string.kazakhstan), "KZT", R.drawable.kazakhstan),
            CountryModel(context.getString(R.string.azerbaidzhan), "AZN", R.drawable.azerbaidzhan),
            CountryModel(context.getString(R.string.armenia), "AMD", R.drawable.armenia),
            CountryModel(context.getString(R.string.georgia), "GEL", R.drawable.georgia),
            CountryModel(context.getString(R.string.uzbekhistan), "UZS", R.drawable.uzbekhistan),
            CountryModel(context.getString(R.string.kirghizstan), "KGS", R.drawable.kirghizstan),
            CountryModel(context.getString(R.string.belarus), "BYN", R.drawable.belarus),
            CountryModel(context.getString(R.string.tajikistan), "TJS", R.drawable.tajikistan),
            CountryModel(context.getString(R.string.moldova), "MDL", R.drawable.moldova),
            CountryModel(context.getString(R.string.turkmenistan), "TMT", R.drawable.turkmenistan),
            CountryModel(context.getString(R.string.ukraine), "UAH", R.drawable.ukraine),
            CountryModel(context.getString(R.string.usa), "USD", R.drawable.usa),
            CountryModel(context.getString(R.string.eu), "EUR", R.drawable.eu)
        )
    }
}
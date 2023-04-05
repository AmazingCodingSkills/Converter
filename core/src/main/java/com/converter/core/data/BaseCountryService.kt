package com.converter.core.data

import com.converter.core.data.favouritecurrencymodel.CountryModel
import com.converter.core.R


object BaseCountryService {
    fun countryList(): List<CountryModel> {
        return listOf(
            CountryModel("Россия", "RUB",R.drawable.russia),
            CountryModel("Казахстан", "KZT", R.drawable.kazakhstan),
            CountryModel("Азербайджан", "AZN", R.drawable.azerbaidzhan),
            CountryModel("Армения", "AMD", R.drawable.armenia),
            CountryModel("Грузия", "GEL", R.drawable.georgia),
            CountryModel("Узбекистан", "UZS", R.drawable.uzbekhistan),
            CountryModel("Кыргызстан", "KGS", R.drawable.kirghizstan),
            CountryModel("Беларусь", "BYN", R.drawable.belarus),
            CountryModel("Таджикистан", "TJS", R.drawable.tajikistan),
            CountryModel("Молдова", "MDL", R.drawable.moldova),
            CountryModel("Туркменистан", "TMT", R.drawable.turkmenistan),
            CountryModel("Украина", "UAH", R.drawable.ukraine)
        )
    }
}
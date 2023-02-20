package com.currency.converter.features.rate.data

import com.currency.converter.features.rate.countryname.CountryModel
import com.example.converter.R

object CountryService {
     fun countryList(): List<CountryModel> {
        val listOfCountries = listOf(
            CountryModel("Россия", "RUB", R.drawable.russia),
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
        return listOfCountries
    }
}
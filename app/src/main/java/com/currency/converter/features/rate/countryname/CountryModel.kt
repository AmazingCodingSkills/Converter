package com.currency.converter.features.rate.countryname

// From backend | Data
data class CountryModel(
    val nameCountry: String,
    val baseCurrency: String,
    val icon: Int
)

// client | ui
data class CountryItem(
    val countryModel: CountryModel,
    val selected: Boolean
)


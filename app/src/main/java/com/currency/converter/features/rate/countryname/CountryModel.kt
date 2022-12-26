package com.currency.converter.features.rate.countryname

data class CountryModel(
    val nameCountry: String,
    val baseCurrency: String
)

data class CountryItem(
    val countryModel: CountryModel,
    val selected: Boolean
)


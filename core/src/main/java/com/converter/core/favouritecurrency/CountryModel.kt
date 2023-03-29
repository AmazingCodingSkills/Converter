package com.currency.converter.features.rate.countryname

import java.io.Serializable


// From backend | Data
data class CountryModel(
    val nameCountry: String,
    val baseCurrency: String,
    val icon: Int
) : Serializable

// client | ui
data class CountryItem(
    val countryModel: CountryModel,
    val selected: Boolean
) : Serializable


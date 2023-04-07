package com.converter.core.country.data

import java.io.Serializable


data class CountryModel(
    val nameCountry: String,
    val baseCurrency: String,
    val icon: Int
) : Serializable

data class CountryItem(
    val countryModel: CountryModel,
    val selected: Boolean
) : Serializable


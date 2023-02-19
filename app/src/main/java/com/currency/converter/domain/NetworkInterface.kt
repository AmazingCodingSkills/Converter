package com.currency.converter.domain

interface NetworkInterface {
    suspend fun isInternetUnavailable(): Boolean
}
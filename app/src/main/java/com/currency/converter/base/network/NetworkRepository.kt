package com.currency.converter.base.network

interface NetworkRepository {
    suspend fun isInternetAvailable(): Boolean
}
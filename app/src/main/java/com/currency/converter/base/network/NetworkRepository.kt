package com.currency.converter.base.network

interface NetworkRepository {
    suspend fun isInternetUnavailable(): Boolean
}
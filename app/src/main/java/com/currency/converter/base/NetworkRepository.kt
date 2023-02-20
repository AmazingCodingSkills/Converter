package com.currency.converter.base

interface NetworkRepository {
    suspend fun isInternetUnavailable(): Boolean
}
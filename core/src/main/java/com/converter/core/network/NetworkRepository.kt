package com.converter.core.network

interface NetworkRepository {
    suspend fun isInternetAvailable(): Boolean
}
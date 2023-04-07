package com.converter.core.network.domain

interface NetworkRepository {
    suspend fun isInternetAvailable(): Boolean
}
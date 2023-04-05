package com.converter.core.presentation.networkfragment

interface NetworkRepository {
    suspend fun isInternetAvailable(): Boolean
}
package com.angga.cloudinary.domain.repository

import com.angga.cloudinary.domain.utils.NetworkState
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun observeNetworkState(): Flow<NetworkState>
}
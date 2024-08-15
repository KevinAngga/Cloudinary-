package com.angga.cloudinary.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.angga.cloudinary.domain.repository.NetworkRepository
import com.angga.cloudinary.domain.utils.NetworkState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkRepositoryImpl  @Inject constructor(
    val context : Context
): NetworkRepository {
    override fun observeNetworkState(): Flow<NetworkState> = callbackFlow {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                launch {
                    send(NetworkState.Connected)
                }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                launch {
                    send(NetworkState.Connected)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                launch {
                    send(NetworkState.Connected)
                }
            }

            override fun onUnavailable() {
                super.onUnavailable()
                launch {
                    send(NetworkState.Connected)
                }
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, callback)

        // Initial state
        val currentState = if (connectivityManager.activeNetwork != null) {
            NetworkState.Connected
        } else {
            NetworkState.Disconnected
        }

        trySend(currentState)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}
package com.angga.cloudinary.domain.utils

sealed class NetworkState {
    object Connected : NetworkState()
    object Disconnected : NetworkState()
}
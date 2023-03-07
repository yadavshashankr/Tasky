package com.portfolio.tasky.usecases

sealed class NetworkStatus{
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}

package com.portfolio.tasky.usecases

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest





class NetworkChangeReceiver constructor(internetStatus: InternetStatus) : BroadcastReceiver() {

    private var _internetStatus : InternetStatus = internetStatus

    override fun onReceive(p0: Context?, p1: Intent?) {
        if(!isOnline(p0)){
            _internetStatus.internetNotAvailable()
        }else{
            _internetStatus.internetNotAvailable()
        }
    }

    private fun isOnline(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private var networkCallback: NetworkCallback = object : NetworkCallback() {

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)

            if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)){
                _internetStatus.internetAvailable()
            }else{
                _internetStatus.internetNotAvailable()
            }
        }
    }
}
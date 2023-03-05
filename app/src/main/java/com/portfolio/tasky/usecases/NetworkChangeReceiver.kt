package com.portfolio.tasky.usecases

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


class NetworkChangeReceiver constructor(internetStatus: InternetStatus) : BroadcastReceiver() {

    private var _internetStatus : InternetStatus = internetStatus

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p0?.let { isOnline(it) } == true){
            _internetStatus.internetAvailable()
        }else{
            _internetStatus.internetNotAvailable()
        }
    }

    private fun isOnline(context: Context): Boolean {
        val result : Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        return result
    }
}
package com.swein.androidkotlintool.main.examples.livedata.networkstatemonitor.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkConnectionReceiver(
    private val onNetworkType: ((String) -> Unit),
    private val onNetworkConnection: ((Boolean) -> Unit)
): BroadcastReceiver() {

    companion object {
        const val NONE = "NONE"
        const val WIFI = "WIFI"
        const val CELLULAR = "CELLULAR"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            onNetworkType(getNetworkType(it))
            onNetworkConnection(isNetworkConnected(it))
        }
    }

    private fun getNetworkType(context: Context): String {
        var result = NONE
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let { networkCapabilities ->
            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    result = WIFI
                }
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    result = CELLULAR
                }
            }
        }
        return result
    }

    private fun isNetworkConnected(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)?.let { networkCapabilities ->

                return when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                    // if you need
//                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                    // if you need
//                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true

                    else -> false
                }

            } ?: return false
        } ?: return false
    }
}
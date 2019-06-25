package com.swein.androidkotlintool.framework.util.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager

class NetworkUtil {

    companion object {

        /**
         * is network enable
         *
         * @param context
         * @return
         */
        fun isNetworkConnected(context: Context?): Boolean {
            if (context != null) {
                val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = manager.activeNetworkInfo
                if (networkInfo != null)
                    return networkInfo.isAvailable
            }
            return false
        }


        /**
         * is WIFI enable
         *
         * @param context
         * @return
         */
        fun isWifiConnected(context: Context?): Boolean {
            if (context != null) {
                val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = manager.activeNetworkInfo
                if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                    return networkInfo.isAvailable
                }
            }
            return false
        }


        /**
         * is Mobile network enable
         *
         * @param context
         * @return
         */
        fun isMobileConnected(context: Context?): Boolean {
            if (context != null) {

                val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = manager.activeNetworkInfo
                if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE)
                    return networkInfo.isAvailable
            }
            return false
        }

        /**
         * get network info
         *
         * @param context
         * @return
         */
        fun getConnectedType(context: Context?): Int {
            if (context != null) {
                val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = manager.activeNetworkInfo
                if (networkInfo != null && networkInfo.isAvailable) {
                    return networkInfo.type
                }
            }
            return -1
        }

        /**
         * get network state: 0 no netowrk, 1 wifi, 2 2G, 3 3G, 4 4G
         *
         *
         * @param context
         * @return
         */
        fun getNetWorkType(context: Context): Int {
            var netType = 0
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo ?: return netType
            val nType = networkInfo.type
            if (nType == ConnectivityManager.TYPE_WIFI) {
                //WIFI
                netType = 1
            }
            else if (nType == ConnectivityManager.TYPE_MOBILE) {
                val nSubType = networkInfo.subtype
                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

                if (nSubType == TelephonyManager.NETWORK_TYPE_LTE && !telephonyManager.isNetworkRoaming) {
                    netType = 4
                }
                else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0 && !telephonyManager.isNetworkRoaming
                ) {
                    netType = 3
                }
                else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA && !telephonyManager.isNetworkRoaming
                ) {
                    netType = 2
                }
                else {
                    netType = 2
                }
            }

            return netType
        }

        /**
         * current wifi ip address that connect with
         * @param context
         * @return
         */
        @SuppressLint("WifiManagerPotentialLeak")
        fun getGatewayIpAddress(context: Context): String {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val dhcp = wifiManager.dhcpInfo
            val ip = dhcp.gateway
            return intIPToStringIP(ip)
        }

        fun intIPToStringIP(ip: Int): String {
            return "${(ip and 0xFF)}.${(ip shr 8 and 0xFF)}.${(ip shr 16 and 0xFF)}.${(ip shr 24 and 0xFF)}"
        }
    }
}
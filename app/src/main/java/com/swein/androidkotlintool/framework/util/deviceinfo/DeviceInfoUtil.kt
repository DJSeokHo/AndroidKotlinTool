package com.swein.androidkotlintool.framework.util.deviceinfo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import com.swein.androidkotlintool.framework.util.network.NetworkUtil
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.*

class DeviceInfoUtil {
    companion object {

        /**
         * must use this method in application first
         *
         * @param context
         * @return
         */
        fun initDeviceScreenDisplayMetricsPixels(context: Context): Boolean {

            val displayMetrics = DisplayMetrics()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                (context as Activity).windowManager.defaultDisplay.getRealMetrics(displayMetrics)
                /*
                    displayMetrics.widthPixels;
                    displayMetrics.heightPixels;
                */
            }

            return true
        }

        fun getScreenSize(context: Context): Point {
            val metrics = context.resources.displayMetrics
            val size = Point()
            size.x = metrics.widthPixels
            size.y = metrics.heightPixels

            return size
        }

        fun getDeviceScreenWidth(context: Context): Int {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return 0
            }

            val displayMetrics = DisplayMetrics()

            (context as Activity).windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }

        fun getDeviceScreenHeight(context: Context): Int {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return 0
            }

            val displayMetrics = DisplayMetrics()

            (context as Activity).windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }

        @SuppressLint("HardwareIds")
        fun getDeviceSerialNum(): String {

            return android.os.Build.SERIAL
        }

        fun getDeviceModel(): String {
            return android.os.Build.MODEL
        }

        fun getDeviceOSVersion(): String {
            return android.os.Build.VERSION.RELEASE
        }

        @SuppressLint("HardwareIds", "MissingPermission")
        fun getDeviceID(context: Context): String {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            val tmDevice: String
            val tmSerial: String
            val androidId: String

            tmDevice = "" + telephonyManager.deviceId
            tmSerial = "" + telephonyManager.simSerialNumber
            androidId = "" + android.provider.Settings.Secure.getString(
                context.contentResolver,
                android.provider.Settings.Secure.ANDROID_ID
            )

            val deviceUuid =
                UUID(androidId.hashCode().toLong(), tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong())

            return deviceUuid.toString()
        }

        fun getDeviceName(): String {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL

            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            }
            else {
                capitalize(manufacturer) + " " + model
            }
        }

        private fun capitalize(s: String?): String {

            if (s == null || s.length == 0) {
                return ""
            }

            val first = s[0]

            return if (Character.isUpperCase(first)) {
                s
            }
            else {
                Character.toUpperCase(first) + s.substring(1)
            }
        }

        @SuppressLint("MissingPermission", "HardwareIds")
        fun getIccid(context: Context): String {

            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var iccid = "N/A"
            iccid = telephonyManager.simSerialNumber
            return iccid
        }

        @SuppressLint("MissingPermission", "HardwareIds")
        fun getNativePhoneNumber(context: Context): String {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var nativePhoneNumber = "N/A"
            nativePhoneNumber = telephonyManager.line1Number
            return nativePhoneNumber
        }


        @SuppressLint("MissingPermission", "HardwareIds")
        fun getPhoneInfo(context: Context): String {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val stringBuffer = StringBuffer()

            stringBuffer.append("\nLine1Number = " + telephonyManager.line1Number)
            stringBuffer.append("\nNetworkOperator = " + telephonyManager.networkOperator)
            stringBuffer.append("\nNetworkOperatorName = " + telephonyManager.networkOperatorName)
            stringBuffer.append("\nSimCountryIso = " + telephonyManager.simCountryIso)
            stringBuffer.append("\nSimOperator = " + telephonyManager.simOperator)
            stringBuffer.append("\nSimOperatorName = " + telephonyManager.simOperatorName)
            stringBuffer.append("\nSimSerialNumber = " + telephonyManager.simSerialNumber)
            stringBuffer.append("\nSubscriberId(IMSI) = " + telephonyManager.subscriberId)
            return stringBuffer.toString()
        }

        @SuppressLint("WifiManagerPotentialLeak")
        fun getIPAddress(context: Context): String? {
            val info = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

            if (info != null && info.isConnected) {
                if (info.type == ConnectivityManager.TYPE_MOBILE) {//2G/3G/4G
                    try {

                        val enumeration = NetworkInterface.getNetworkInterfaces()

                        while (enumeration.hasMoreElements()) {

                            val networkInterface = enumeration.nextElement()
                            val enumIpAddr = networkInterface.inetAddresses

                            while (enumIpAddr.hasMoreElements()) {

                                val inetAddress = enumIpAddr.nextElement()

                                if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                    return inetAddress.getHostAddress()
                                }
                            }
                        }
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                else if (info.type == ConnectivityManager.TYPE_WIFI) { // WiFi
                    val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                    val wifiInfo = wifiManager.connectionInfo
                    return NetworkUtil.intIPToStringIP(wifiInfo.ipAddress)
                }
            }
            else {
                // no network
            }
            return null
        }
    }
}
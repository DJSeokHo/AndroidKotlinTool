package com.swein.androidkotlintool.framework.module.location

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Looper
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.swein.androidkotlintool.framework.module.location.service.LocationService
import com.swein.androidkotlintool.framework.util.log.ILog
import java.lang.ref.WeakReference

object LocationManager {

    private const val TAG = "LocationManager"

    private lateinit var activity: WeakReference<Activity>
    private lateinit var locationRequest: LocationRequest
    private lateinit var onUpdateLocation: WeakReference<(latitude: Double, longitude: Double) -> Unit>

    private var interval: Long = 10000 // 10000 is recommend
    private var fastestInterval: Long = 1000 // 5000 is recommend
    private var priority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY

    private val locationCallBack = object: LocationCallback() {
        override fun onLocationAvailability(locationAvailability: LocationAvailability) {
            super.onLocationAvailability(locationAvailability)
//            ILog.debug(TAG, "${locationAvailability.isLocationAvailable}")

            if (!locationAvailability.isLocationAvailable) {

                activity.get()?.let {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    it.startActivity(intent)

                    stop(it)
                }
            }
        }

        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            onUpdateLocation.get()?.invoke(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude)

        }
    }

    object Builder {

        fun build(): Builder {
            return this
        }

        fun setInterval(interval: Long): Builder {
            LocationManager.interval = interval
            return this
        }

        fun setFastestInterval(fastestInterval: Long): Builder {
            LocationManager.fastestInterval = fastestInterval
            return this
        }

        fun setPriority(priority: Int): Builder {
            LocationManager.priority = priority
            return this
        }

        fun create(activity: Activity): LocationManager {
            LocationManager.activity = WeakReference(activity)
            locationRequest = LocationRequest.create()
                .setInterval(interval) // 此方法以毫秒为单位，设置应用接收位置信息更新的频率。请注意，为了优化电池电量的使用，位置信息的更新频率可能会高于或低于此频率，此外也可能完全不更新位置信息（例如，当设备没有网络连接时）。
                .setFastestInterval(fastestInterval) // 此方法以毫秒为单位，设置应用处理位置信息更新的最快频率。除非以快于 setInterval() 中指定的频率接收更新对应用有益，否则您无需调用此方法。
                .setPriority(priority)
            /*
                PRIORITY_BALANCED_POWER_ACCURACY - 使用此设置可以请求城市街区级别的定位精确度，即大约 100 米。这是一个粗略的准确度，消耗的电量可能会比较少。使用此设置时，位置信息服务可能会使用 WLAN 和手机基站来进行定位。
                PRIORITY_HIGH_ACCURACY - 使用此设置可以请求尽可能精确的位置信息。使用此设置时，位置信息服务更有可能使用 GPS 确定位置。
                PRIORITY_LOW_POWER - 使用此设置可以请求城市级别的定位精确度，即大约 10 公里。这是一个粗略的准确度，消耗的电量可能会比较少。
                PRIORITY_NO_POWER - 如果您不希望增加耗电量，又想及时获得可用的位置信息更新，请使用此设置。使用此设置时，您的应用不会触发任何位置信息更新，但会接收其他应用触发的位置信息更新。
             */

            return LocationManager
        }

    }

    fun request(withForegroundService: Boolean, onUpdateLocation: ((latitude: Double, longitude: Double) -> Unit)) {
        this.onUpdateLocation = WeakReference(onUpdateLocation)
        if (!withForegroundService) {
            requestWithoutService()
        }
        else {
            requestWithService()
        }
    }

    fun stop(activity: Activity) {

        LocationService.stopLocatingService(activity)
        removeCallback(activity)
    }

    private fun removeCallback(activity: Activity) {
        LocationServices.getFusedLocationProviderClient(activity).removeLocationUpdates(locationCallBack)
    }

    private fun requestWithoutService() {

        activity.get()?.let {

            if (ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            LocationServices.getFusedLocationProviderClient(it).requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper())
        }
    }

    private fun requestWithService() {

        activity.get()?.let { context ->
            LocationService.startLocatingWithService(context, delegate = { serviceContext ->

                if (ActivityCompat.checkSelfPermission(
                        serviceContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        serviceContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return@startLocatingWithService
                }

                LocationServices.getFusedLocationProviderClient(serviceContext).requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper())

            })
        }

    }
}
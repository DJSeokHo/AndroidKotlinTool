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

    private var interval: Long = 2000
    private var fastestInterval: Long = 1000
    private var priority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY

    private val locationCallBack = object: LocationCallback() {
        override fun onLocationAvailability(locationAvailability: LocationAvailability) {
            super.onLocationAvailability(locationAvailability)
            ILog.debug(TAG, "${locationAvailability.isLocationAvailable}")

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
                .setInterval(interval)
                .setFastestInterval(fastestInterval)
                .setPriority(priority)

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
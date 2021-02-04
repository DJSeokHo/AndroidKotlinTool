package com.swein.androidkotlintool.framework.util.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import com.swein.androidkotlintool.framework.util.location.model.LocationModel
import com.swein.androidkotlintool.framework.util.log.ILog

interface SHLocationDelegate {
    fun onLocation(latitude: Double, longitude: Double, time: Long)
}

class SHLocation {

    companion object {
        private const val TAG = "SHLocation"

        /* gps max wait time */
        const val GPS_WATI_TIME_ONE_MINUTES = 1000 * 60 * 1

        /* if network accuracy small than 100 meter than will use it */
        /* 20 is same as gps */
        const val NETWORK_ACCURACY_MIN = 20.0f
    }

    private var locationManager: LocationManager? = null
    private var context: Context? = null
    private var shLocationDelegate: SHLocationDelegate? = null

    private var bestLocation: Location? = null

    private var requestLocationJustOnce: Boolean? = null

    private val networkLocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            if (bestLocation == null) {
                bestLocation = location
            }
            else {
                bestLocation = getBestLocation(bestLocation!!, location)
            }

            val locationModel = getLocationModel(location)

            if (requestLocationJustOnce!!) {
                clear()
            }

            ILog.debug(TAG, locationModel.toString())
            showLocation(location, shLocationDelegate!!)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String) {

        }

        override fun onProviderDisabled(provider: String) {

        }
    }

    private val gpsLocationListener = object: LocationListener {

        override fun onLocationChanged(location: Location) {

            if (bestLocation == null) {
                bestLocation = location
            }
            else {
                bestLocation = getBestLocation(bestLocation!!, location)
            }

            val locationModel = getLocationModel(bestLocation!!)

            if (requestLocationJustOnce!!) {
                clear()
            }

            ILog.debug(TAG, locationModel.toString())
            showLocation(location!!, shLocationDelegate!!)

        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String) {
            requestLocation()
        }

        override fun onProviderDisabled(provider: String) {

        }
    }

    constructor(context: Context, shLocationDelegate: SHLocationDelegate, requestLocationJustOnce: Boolean) {
        this.context = context
        this.requestLocationJustOnce = requestLocationJustOnce
        this.shLocationDelegate = shLocationDelegate
    }

    private fun getLocationModel(location: Location): LocationModel {

        val locationModel = LocationModel()
        locationModel.provider = location.provider
        locationModel.latitude = location.latitude
        locationModel.longitude = location.longitude

        if (location.hasAccuracy()) {
            locationModel.accuracy = location.accuracy
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(location.hasVerticalAccuracy()) {
                locationModel.verticalAccuracy = location.verticalAccuracyMeters
            }
        }

        return locationModel
    }

    @SuppressLint("MissingPermission")
    fun requestLocation() {

        if (locationManager == null) {
            locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        if (locationManager == null) {
            return
        }

        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, networkLocationListener)
        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, gpsLocationListener)
    }

    private fun showLocation(location: Location, shLocationDelegate: SHLocationDelegate) {
        ILog.debug(TAG, location.latitude.toString() + " " + location.longitude)
        shLocationDelegate.onLocation(location.longitude, location.latitude, System.currentTimeMillis())
    }

    private fun getBestLocation(knownPosition: Location, newPosition: Location): Location {

        if (!knownPosition.hasAccuracy() && !newPosition.hasAccuracy()) {
            return newPosition
        }

        return if (knownPosition.accuracy < newPosition.accuracy) {
            knownPosition
        } else {
            newPosition
        }
    }

    private fun clear() {

        locationManager?.let {
            it.removeUpdates(networkLocationListener)
            it.removeUpdates(gpsLocationListener)
        }

        bestLocation = null
    }

    protected fun finalize() {
        clear()
    }
}
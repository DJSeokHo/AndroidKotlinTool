package com.swein.androidkotlintool.framework.module.location.demo

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.LocationRequest
import com.swein.androidkotlintool.framework.module.location.LocationManager
import com.swein.androidkotlintool.framework.util.log.ILog
import java.lang.ref.WeakReference

// 观察者
class LocationObserver(private val activity: WeakReference<Activity>, private val onRequestPermission: () -> Unit): LifecycleObserver {

    companion object {
        private const val TAG = "LocationObserver"
    }

    var isPermissionGranted = false

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        ILog.debug(TAG, "onCreate, request permission")
        onRequestPermission()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {

        isPermissionGranted.let {
            ILog.debug(TAG, "onResume, continue locating")
            requestLocating()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {

        isPermissionGranted.let {
            ILog.debug(TAG, "onPause, stop locating")
            stopLocating()
        }
    }

    private fun requestLocating() {

        activity.get()?.let {
            LocationManager.Builder
                .setInterval(4000)
                .setFastestInterval(2000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .create(it)
                .request(false) { latitude, longitude ->
                    ILog.debug(TAG, "location update $latitude, and longitude is secret :)")
                }
        }

    }

    private fun stopLocating() {

        activity.get()?.let {
            LocationManager.stop(it)

        }
    }

}
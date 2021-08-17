package com.swein.androidkotlintool.framework.module.location.demo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationRequest
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.module.location.LocationManager
import com.swein.androidkotlintool.framework.module.location.geo.SHGeoCoder
import com.swein.androidkotlintool.framework.module.location.service.LocationService
import com.swein.androidkotlintool.framework.util.log.ILog
import com.swein.androidkotlintool.main.examples.permissionexample.PermissionManager


class LocationDemoActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LocationDemoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_demo)

        findViewById<Button>(R.id.buttonStart).setOnClickListener {

//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
//                PermissionManager.requestPermission(
//                    this,
//                    9999, "", "", "",
//                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//                ) {
//
//                    requestLocation()
//                }
//            }
//            else {
//                PermissionManager.requestPermission(
//                    this,
//                    9999, "", "", "",
//                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION)
//                ) {
//
//                    requestLocation()
//                }
//            }

        }

        findViewById<Button>(R.id.buttonStop).setOnClickListener {

            LocationManager.stop(this)

        }
    }

    private fun requestLocation() {
//        LocationManager.Builder
//            .setInterval(4000)
//            .setFastestInterval(2000)
//            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//            .create(this)
//            .request(true, onUpdateLocation = { latitude, longitude ->
//                ILog.debug(TAG, "location update $latitude, $longitude")
//
//
//                // if you want to request location just once, then
////                LocationManager.stop(this)
//            })

        LocationManager.Builder
            .setInterval(4000)
            .setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .create(this)
            .request(false, onUpdateLocation = { latitude, longitude ->
//                    ILog.debug(TAG, "location update $latitude, and longitude is secret :)")

                val shGeoCoder = SHGeoCoder(this)
                val addressList = shGeoCoder.getFromLocation(latitude, longitude, 10)
                for (address in addressList) {
                    ILog.debug(TAG, "============================")
                    ILog.debug(TAG, address.toString())
                }
            })
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        PermissionManager.onActivityResult(requestCode, resultCode)
//        super.onActivityResult(requestCode, resultCode, data)
//    }

//    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
////        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
////
////        manager.get
////        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
////            if (serviceClass.name == service.service.className) {
////                return true
////            }
////        }
////        return false
//
//
//    }
}
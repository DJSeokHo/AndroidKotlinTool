package com.swein.androidkotlintool.framework.module.location.demo

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.main.examples.permissionexample.PermissionManager
import java.lang.ref.WeakReference

/**
 * 被观察者
 */
class LocationWithLifecycleDemoActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LocationWithLifecycleDemoActivity"
    }

    private lateinit var locationObserver: LocationObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_with_lifecycle_demo)

        locationObserver = LocationObserver(WeakReference(this)) {

            if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    PermissionManager.requestPermission(
                        this,
                        9999, "", "", "",
                        listOf(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    ) {

                        locationObserver.isPermissionGranted = true
                    }
                }
                else {
                    PermissionManager.requestPermission(
                        this,
                        9999, "", "", "",
                        listOf(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                    ) {

                        locationObserver.isPermissionGranted = true
                    }
                }

            }

        }

        lifecycle.addObserver(locationObserver)

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        PermissionManager.onActivityResult(requestCode, resultCode)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
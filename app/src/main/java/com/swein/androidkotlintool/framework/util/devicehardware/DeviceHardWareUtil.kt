package com.swein.androidkotlintool.framework.util.devicehardware

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat

class DeviceHardWareUtil {

    companion object {

        /**
         * GPS
         */
        fun isGPSTurnOn(context: Context): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        /**
         * GPS
         */
        fun turnOnGPS(context: Context) {
            val intent = Intent()
            intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            try {
                context.startActivity(intent)
            }
            catch (ex: ActivityNotFoundException) {

                ex.printStackTrace()

                intent.action = Settings.ACTION_SETTINGS
                try {
                    context.startActivity(intent)
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        /**
         * Camera
         */
        fun hasCameraDevice(context: Context): Boolean {
            return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
        }

        /**
         * Camera
         */
        fun isAutoFocusSupported(params: Camera.Parameters): Boolean {
            val modes = params.supportedFocusModes
            return modes.contains(Camera.Parameters.FOCUS_MODE_AUTO)
        }

        /**
         * Camera
         */
        fun turnOnFlashLight(camera: Camera) {
            val parameters = camera.parameters
            parameters.flashMode = Camera.Parameters.FLASH_MODE_TORCH
            camera.parameters = parameters
        }

        /**
         * Camera
         */
        fun turnOffFlashLight(camera: Camera) {
            val parameters = camera.parameters
            parameters.flashMode = Camera.Parameters.FLASH_MODE_OFF
            camera.parameters = parameters
        }

        /**
         * Phone Call
         */
        fun callWithDialog(context: Context, phone: String) {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        /**
         * Phone Call
         */
        fun call(context: Context, phone: String) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            context.startActivity(intent)
        }
    }
}
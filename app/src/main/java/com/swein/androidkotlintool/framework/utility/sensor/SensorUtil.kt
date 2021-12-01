package com.swein.androidkotlintool.framework.utility.sensor

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class SensorUtil {

    companion object {

        fun isLandscape(activity: Activity, landscape: Runnable, portrait: Runnable) {

            val sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager

            sensorManager.registerListener(object : SensorEventListener {
                var orientation = -1

                override fun onSensorChanged(event: SensorEvent) {
                    if (event.values[1] < 6.5 && event.values[1] > -6.5) {
                        if (orientation != 1) {
                            landscape.run()
                        }
                        orientation = 1
                    }
                    else {
                        if (orientation != 0) {
                            portrait.run()
                        }
                        orientation = 0
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                    // TODO Auto-generated method stub

                }
            }, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME)
        }
    }
}
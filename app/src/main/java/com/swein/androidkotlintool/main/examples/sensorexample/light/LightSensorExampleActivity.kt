package com.swein.androidkotlintool.main.examples.sensorexample.light

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

class LightSensorExampleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LightSensorExampleActivity"
    }

    private val textView: TextView by lazy {
        findViewById(R.id.textView)
    }
    
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private var sensorEventListener = object : SensorEventListener {

        override fun onSensorChanged(sensorEvent: SensorEvent?) {
            // The light sensor returns a single value.
            // Many sensors return 3 values, one for each axis.
            sensorEvent?.let {
                val lux = it.values[0]
                val lux1 = it.values[1]
                val lux2 = it.values[2]
                ILog.debug(TAG, "lux is $lux $lux1 $lux2 total: ${it.values.size}")

                // 光线感应传感器检测实时的光线强度，光强单位是lux(勒克司度)，其物理意义是照射到单位面积上的光通量。

                textView.text = "${it.values[0]}\n${it.values[1]}\n${it.values[2]}"
            }

            // Do something with this sensor value.
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            // Do something here if sensor accuracy changes.

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_sensor_example)

        initSensor()
    }

    override fun onResume() {
        super.onResume()

        lightSensor?.also { light ->
            sensorManager.registerListener(sensorEventListener, light, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }

    private fun initSensor() {
        lightSensor = getSensor(Sensor.TYPE_LIGHT, "TYPE_LIGHT")
    }

    private fun getSensor(sensorType: Int, sensorTypeString: String): Sensor? {
        ILog.debug(TAG, "\n\n\n==========================================")
        ILog.debug(TAG, "getSensor sensorType $sensorTypeString")

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(sensorType) != null) {
            // Success! There's a magnetometer.

            val sensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER)
            if (sensors.isEmpty()) {
                ILog.debug(TAG, "Sensor $sensorTypeString is null")
                return null
            }
            for (sensor in sensors) {
                ILog.debug(TAG, "${sensor.name} ${sensor.stringType}")
                // getMinDelay() 传感器可用于检测数据的最小时间间隔（以微秒为单位）
                // 如果调用 getMinDelay() 方法时，传感器返回 0，则说明传感器不是流式传感器，因为它仅在所检测的参数发生变化时才会报告数据
                ILog.debug(TAG, "info: ${sensor.vendor}, ${sensor.version} | ${sensor.minDelay} | ${sensor.maxDelay} | ${sensor.maximumRange}")
            }

            return sensors[0]
        }
        else {
            ILog.debug(TAG, "Sensor $sensorTypeString is null")
            return null
        }

    }


}
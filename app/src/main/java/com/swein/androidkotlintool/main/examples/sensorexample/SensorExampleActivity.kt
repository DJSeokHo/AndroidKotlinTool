package com.swein.androidkotlintool.main.examples.sensorexample

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

/**
您可以利用 Android 传感器框架来访问这些传感器并获取传感器的原始数据。传感器框架是 android.hardware 软件包的一部分，包含了以下类和接口：

SensorManager
您可以使用这个类来创建传感器服务的实例。该类提供了各种方法来访问和列出传感器，注册和取消注册传感器事件监听器，以及获取屏幕方向信息。它还提供了几个传感器常量，用于报告传感器精确度，设置数据采集频率和校准传感器。
Sensor
您可以使用这个类来创建特定传感器的实例。该类提供了各种方法来确定传感器的特性。
SensorEvent
系统使用这个类来创建传感器事件对象，该对象提供有关传感器事件的信息。传感器事件对象中包含以下信息：原始传感器数据、生成事件的传感器类型、数据的准确度和事件的时间戳。
SensorEventListener
您可以使用此接口创建两种回调方法，以在传感器值或传感器精确度发生变化时接收通知（传感器事件）。

传感器	类型	说明	常见用途
TYPE_ACCELEROMETER	硬件	测量在所有三个物理轴向（x、y 和 z）上施加在设备上的加速力（包括重力），以 m/s2 为单位。	动态检测（摇晃、倾斜等）。
TYPE_AMBIENT_TEMPERATURE	硬件	以摄氏度 (°C) 为单位测量环境室温。请参见下面的备注。	监测气温。
TYPE_GRAVITY	软件或硬件	测量在所有三个物理轴向（x、y、z）上施加在设备上的重力，单位为 m/s2。	动态检测（摇晃、倾斜等）。
TYPE_GYROSCOPE	硬件	测量设备在三个物理轴向（x、y 和 z）上的旋转速率，以 rad/s 为单位。	旋转检测（旋转、转动等）。
TYPE_LIGHT	硬件	测量环境光级（照度），以 lx 为单位。	控制屏幕亮度。
TYPE_LINEAR_ACCELERATION	软件或硬件	测量在所有三个物理轴向（x、y 和 z）上施加在设备上的加速力（不包括重力），以 m/s2 为单位。	监测单个轴向上的加速度。
TYPE_MAGNETIC_FIELD	硬件	测量所有三个物理轴向（x、y、z）上的环境地磁场，以 μT 为单位。	创建罗盘。
TYPE_ORIENTATION	软件	测量设备围绕所有三个物理轴（x、y、z）旋转的度数。从 API 级别 3 开始，您可以结合使用重力传感器、地磁场传感器和 getRotationMatrix() 方法来获取设备的倾角矩阵和旋转矩阵。	确定设备位置。
TYPE_PRESSURE	硬件	测量环境气压，以 hPa 或 mbar 为单位。	监测气压变化。
TYPE_PROXIMITY	硬件	测量物体相对于设备显示屏幕的距离，以 cm 为单位。该传感器通常用于确定手机是否被举到人的耳边。	通话过程中手机的位置。
TYPE_RELATIVE_HUMIDITY	硬件	测量环境的相对湿度，以百分比 (%) 表示。	监测露点、绝对湿度和相对湿度。
TYPE_ROTATION_VECTOR	软件或硬件	通过提供设备旋转矢量的三个元素来检测设备的屏幕方向。	动态检测和旋转检测。
TYPE_TEMPERATURE	硬件	测量设备的温度，以摄氏度 (°C) 为单位。该传感器的实现因设备而异。在 API 级别 14 中，该传感器已被 TYPE_AMBIENT_TEMPERATURE 传感器取代	监测温度。


传感器的准确度发生了变化。
在这种情况下，系统会调用 onAccuracyChanged() 方法，为您提供对于发生变化的 Sensor 对象的引用以及传感器的新准确度。
准确度由以下 4 个状态常量之一表示：SENSOR_STATUS_ACCURACY_LOW、SENSOR_STATUS_ACCURACY_MEDIUM、SENSOR_STATUS_ACCURACY_HIGH 或 SENSOR_STATUS_UNRELIABLE。

传感器报告了新值。
在这种情况下，系统会调用 onSensorChanged() 方法，为您提供 SensorEvent 对象。
SensorEvent 对象包含关于新传感器数据的信息，包括：数据的准确度、生成数据的传感器、生成数据的时间戳以及传感器记录的新数据。


 */
class SensorExampleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SensorExampleActivity"
    }

    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_example)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
//        for (deviceSensor in deviceSensors) {
//            ILog.debug(TAG, "${deviceSensor.name} ${deviceSensor.stringType}")
//        }

        // TYPE_ACCELEROMETER	硬件	测量在所有三个物理轴向（x、y 和 z）上施加在设备上的加速力（包括重力），以 m/s2 为单位。	动态检测（摇晃、倾斜等）。
        getSensor(Sensor.TYPE_ACCELEROMETER, "TYPE_ACCELEROMETER")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // TYPE_ACCELEROMETER_UNCALIBRATED 硬件	测量在所有三个物理轴向（x、y 和 z）上施加在设备上的加速力（包括重力），以 m/s2 为单位。	动态检测（摇晃、倾斜等）。
            getSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED, "TYPE_ACCELEROMETER_UNCALIBRATED")
        }

        // TYPE_AMBIENT_TEMPERATURE	硬件	以摄氏度 (°C) 为单位测量环境室温。请参见下面的备注。	监测气温。
        getSensor(Sensor.TYPE_AMBIENT_TEMPERATURE, "TYPE_AMBIENT_TEMPERATURE")

        getSensor(Sensor.TYPE_DEVICE_PRIVATE_BASE, "TYPE_DEVICE_PRIVATE_BASE")
        getSensor(Sensor.TYPE_GAME_ROTATION_VECTOR, "TYPE_GAME_ROTATION_VECTOR")
        getSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, "TYPE_GEOMAGNETIC_ROTATION_VECTOR")

        // 软件或硬件	测量在所有三个物理轴向（x、y、z）上施加在设备上的重力，单位为 m/s2。	动态检测（摇晃、倾斜等）。
        getSensor(Sensor.TYPE_GRAVITY, "TYPE_GRAVITY")

        // 硬件	测量设备在三个物理轴向（x、y 和 z）上的旋转速率，以 rad/s 为单位。	旋转检测（旋转、转动等）。
        getSensor(Sensor.TYPE_GYROSCOPE, "TYPE_GYROSCOPE")

        // 硬件	测量设备在三个物理轴向（x、y 和 z）上的旋转速率，以 rad/s 为单位。	旋转检测（旋转、转动等）。
        getSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED, "TYPE_GYROSCOPE_UNCALIBRATED")

        getSensor(Sensor.TYPE_HEART_BEAT, "TYPE_HEART_BEAT")

        getSensor(Sensor.TYPE_HEART_RATE, "TYPE_HEART_RATE")

        // TYPE_ROTATION_VECTOR	软件或硬件	通过提供设备旋转矢量的三个元素来检测设备的屏幕方向。	动态检测和旋转检测。
        getSensor(Sensor.TYPE_ROTATION_VECTOR, "TYPE_ROTATION_VECTOR")

        // 硬件	测量环境气压，以 hPa 或 mbar 为单位。	监测气压变化。
        getSensor(Sensor.TYPE_PRESSURE, "TYPE_PRESSURE")

        // 硬件	测量物体相对于设备显示屏幕的距离，以 cm 为单位。该传感器通常用于确定手机是否被举到人的耳边。	通话过程中手机的位置。
        getSensor(Sensor.TYPE_PROXIMITY, "TYPE_PROXIMITY")

        // TYPE_RELATIVE_HUMIDITY	硬件	测量环境的相对湿度，以百分比 (%) 表示。	监测露点、绝对湿度和相对湿度。
        getSensor(Sensor.TYPE_RELATIVE_HUMIDITY, "TYPE_RELATIVE_HUMIDITY")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getSensor(Sensor.TYPE_HINGE_ANGLE, "TYPE_HINGE_ANGLE")
        }

        // 硬件	测量环境光级（照度），以 lx 为单位。	控制屏幕亮度。
        getSensor(Sensor.TYPE_LIGHT, "TYPE_LIGHT")

        // TYPE_LINEAR_ACCELERATION	软件或硬件	测量在所有三个物理轴向（x、y 和 z）上施加在设备上的加速力（不包括重力），以 m/s2 为单位。	监测单个轴向上的加速度。
        getSensor(Sensor.TYPE_LINEAR_ACCELERATION, "TYPE_LINEAR_ACCELERATION")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getSensor(Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT, "TYPE_LOW_LATENCY_OFFBODY_DETECT")
        }

        // TYPE_MAGNETIC_FIELD	硬件	测量所有三个物理轴向（x、y、z）上的环境地磁场，以 μT 为单位。	创建罗盘。
        getSensor(Sensor.TYPE_MAGNETIC_FIELD, "TYPE_MAGNETIC_FIELD")

        // TYPE_MAGNETIC_FIELD	硬件	测量所有三个物理轴向（x、y、z）上的环境地磁场，以 μT 为单位。	创建罗盘。
        getSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, "TYPE_MAGNETIC_FIELD_UNCALIBRATED")

        getSensor(Sensor.TYPE_MOTION_DETECT, "TYPE_MOTION_DETECT")

    }


    private fun getSensor(sensorType: Int, sensorTypeString: String): Sensor? {
        ILog.debug(TAG, "\n\n\n==========================================")
        ILog.debug(TAG, "getSensor sensorType $sensorTypeString")

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
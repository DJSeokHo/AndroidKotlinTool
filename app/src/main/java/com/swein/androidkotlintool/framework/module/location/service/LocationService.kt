package com.swein.androidkotlintool.framework.module.location.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.swein.androidkotlintool.R
import java.lang.ref.WeakReference

class LocationService: Service() {

    companion object {

        var isLocationServiceRunning = false

        private const val LOCATION_SERVICE_ID = 666
        private const val ACTION_START_LOCATION_SERVICE = "startLocationService"
        private const val ACTION_STOP_LOCATION_SERVICE = "stopLocationService"

        private lateinit var delegate: WeakReference<(serviceContext: Service) -> Unit>

        fun startLocatingWithService(
            context: Context,
            channelId: String = "location_notification_channel_id",
            iconResource: Int = R.mipmap.ic_launcher,
            title: String = "Location Service",
            defaults: Int = NotificationCompat.DEFAULT_ALL,
            contentText: String = "Updating Location",
            versionOName: String = "Location service",
            versionODescription: String = "This channel is used by location service",
            delegate: (serviceContext: Service) -> Unit
        ) {

            this.delegate = WeakReference(delegate)

            Intent(context, LocationService::class.java).apply {
                this.action = ACTION_START_LOCATION_SERVICE

                val bundle = Bundle()

                bundle.putString("channelId", channelId)
                bundle.putInt("iconResource", iconResource)
                bundle.putString("title", title)
                bundle.putInt("defaults", defaults)
                bundle.putString("contentText", contentText)
                bundle.putString("versionOName", versionOName)
                bundle.putString("versionODescription", versionODescription)

                this.putExtra("bundle", bundle)

                context.startService(this)
            }
        }

        fun stopLocatingService(context: Context) {
            Intent(context, LocationService::class.java).apply {
                this.action = ACTION_STOP_LOCATION_SERVICE
                context.startService(this)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            it.action?.let { action ->
                if (action == ACTION_START_LOCATION_SERVICE) {

                    val bundle = it.getBundleExtra("bundle")!!
                    startLocationService(
                        bundle["channelId"] as String,
                        bundle["iconResource"] as Int,
                        bundle["title"] as String,
                        bundle["defaults"] as Int,
                        bundle["contentText"] as String,
                        bundle["versionOName"] as String,
                        bundle["versionODescription"] as String,
                    )

                } else if (action == ACTION_STOP_LOCATION_SERVICE) {
                    stopLocationService()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startLocationService(
        channelId: String, iconResource: Int, title: String,
        defaults: Int, contentText: String, versionOName: String, versionODescription: String
    ) {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent()
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(applicationContext, channelId)

        builder.setSmallIcon(iconResource)
        builder.setContentTitle(title)
        builder.setDefaults(defaults)
        builder.setContentText(contentText)
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(false)
        builder.priority = NotificationCompat.PRIORITY_MAX

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                versionOName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = versionODescription
            notificationManager.createNotificationChannel(notificationChannel)
        }

        delegate.get()?.invoke(this)

        startForeground(LOCATION_SERVICE_ID, builder.build())

        isLocationServiceRunning = true
    }

    private fun stopLocationService() {

        isLocationServiceRunning = false

        stopForeground(true)
        stopSelf()
    }

}
package com.swein.androidkotlintool.main.examples.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog
import com.swein.androidkotlintool.framework.utility.intent.IntentUtility
import java.util.*

class AlarmManagerExampleActivity : AppCompatActivity() {

    private val buttonElapsedRealTimeWithRepeat: Button by lazy {
        findViewById(R.id.buttonElapsedRealTimeWithRepeat)
    }

    private val buttonElapsedRealTime: Button by lazy {
        findViewById(R.id.buttonElapsedRealTime)
    }

    private val buttonRTCWithRepeat: Button by lazy {
        findViewById(R.id.buttonRTCWithRepeat)
    }

    private val buttonRTC: Button by lazy {
        findViewById(R.id.buttonRTC)
    }

    private val buttonCancel: Button by lazy {
        findViewById(R.id.buttonCancel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_manager_example)

        buttonElapsedRealTimeWithRepeat.setOnClickListener {

//             setInexactRepeating() instead setRepeating()
            toggleElapsedRealTimeAlarm(
                this,
                repeat = true,
                weakUp = false,
                pendingIntent =  IntentUtility.createPendingIntent(
                    IntentUtility.PendingIntentEnum.ACTIVITY,
                    this,
                    111,
                    Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749,-122.4192?z=11")).apply {
                        `package` = "com.google.android.apps.maps"
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                ),
                triggerAfterSecond = 5,
                intervalSecond = 5
            )

        }

        buttonElapsedRealTime.setOnClickListener {

            // one time alarm
            toggleElapsedRealTimeAlarm(
                this,
                repeat = false,
                weakUp = false,
                pendingIntent = IntentUtility.createPendingIntent(
                    IntentUtility.PendingIntentEnum.ACTIVITY,
                    this,
                    333,
//                    Intent(this, BroadcastReceiverExample::class.java)
                    Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749,-122.4192?z=11")).apply {
                        `package` = "com.google.android.apps.maps"
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                ),
                triggerAfterSecond = 5,
            )

        }

        buttonRTCWithRepeat.setOnClickListener {

            toggleRTCAlarm(
                this,
                12,
                15,
                repeat = true,
                weakUp = true,
                pendingIntent = IntentUtility.createPendingIntent(
                    IntentUtility.PendingIntentEnum.ACTIVITY,
                    this,
                    555,
//                    Intent(this, BroadcastReceiverExample::class.java)
                    Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749,-122.4192?z=11")).apply {
                        `package` = "com.google.android.apps.maps"
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            )

        }

        buttonRTC.setOnClickListener {

            toggleRTCAlarm(
                this,
                12,
                15,
                repeat = false,
                weakUp = true,
                pendingIntent = IntentUtility.createPendingIntent(
                    IntentUtility.PendingIntentEnum.ACTIVITY,
                    this,
                    777,
//                    Intent(this, BroadcastReceiverExample::class.java)
                    Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749,-122.4192?z=11")).apply {
                        `package` = "com.google.android.apps.maps"
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            )
        }

        buttonCancel.setOnClickListener {
            cancelAlarm(this, IntentUtility.createPendingIntent(
                IntentUtility.PendingIntentEnum.ACTIVITY,
                this,
                333,
//                    Intent(this, BroadcastReceiverExample::class.java)
                Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749,-122.4192?z=11")).apply {
                    `package` = "com.google.android.apps.maps"
//                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            ))
        }
    }

    private fun toggleElapsedRealTimeAlarm(
        context: Context,
        repeat: Boolean,
        weakUp: Boolean,
        pendingIntent: PendingIntent,
        triggerAfterSecond: Int,
        intervalSecond: Int = 60, // default minimum is 60 seconds!!
    ) {

        val alarmManager = getAlarmManager(context)
        alarmManager.cancel(pendingIntent)

        if (!repeat) {

            alarmManager.set(
                if (weakUp) {
                    AlarmManager.ELAPSED_REALTIME_WAKEUP
                }
                else {
                    AlarmManager.ELAPSED_REALTIME
                },
                SystemClock.elapsedRealtime() + triggerAfterSecond * 1000,
                pendingIntent
            )

            return
        }

        alarmManager.setInexactRepeating(
            if (weakUp) {
                AlarmManager.ELAPSED_REALTIME_WAKEUP
            }
            else {
                AlarmManager.ELAPSED_REALTIME
            },
            SystemClock.elapsedRealtime() + triggerAfterSecond * 1000,
            intervalSecond * 1000L,
            pendingIntent
        )
    }


    private fun toggleRTCAlarm(
        context: Context,
        hour24: Int,
        minute: Int,
        repeat: Boolean,
        weakUp: Boolean,
        pendingIntent: PendingIntent,
        intervalSecond: Int = 60, // default minimum is 60 seconds!!
    ) {

        val alarmManager = getAlarmManager(context)
        alarmManager.cancel(pendingIntent)

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour24)
            set(Calendar.MINUTE, minute)
        }

        if (!repeat) {

            alarmManager.set(
                if (weakUp) {
                    AlarmManager.RTC_WAKEUP
                }
                else {
                    AlarmManager.RTC
                },
                calendar.timeInMillis,
                pendingIntent
            )

            return
        }

        alarmManager.setInexactRepeating(
            if (weakUp) {
                AlarmManager.ELAPSED_REALTIME_WAKEUP
            }
            else {
                AlarmManager.ELAPSED_REALTIME
            },
            calendar.timeInMillis,
            intervalSecond * 1000L,
            pendingIntent
        )

    }

    private fun getAlarmManager(context: Context): AlarmManager {
        return context.getSystemService(ALARM_SERVICE) as AlarmManager
    }

    /**
     * 如果 PendingIntent 是使用 FLAG_ONE_SHOT 创建的，就无法将其取消。
     */
    private fun cancelAlarm(context: Context, pendingIntent: PendingIntent) {
        ILog.debug("???", "cancelAlarm")
        getAlarmManager(context).cancel(pendingIntent)
        pendingIntent.cancel()
    }
}




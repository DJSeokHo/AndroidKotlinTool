package com.swein.androidkotlintool.main.examples.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import com.swein.androidkotlintool.R

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_manager_example)

        buttonElapsedRealTimeWithRepeat.setOnClickListener {

            // setInexactRepeating() instead setRepeating()
//            toggleElapsedRealTimeAlarm(
//                withRepeat = true,
//                withWeakUp = false,
//                pendingIntent =  createPendingIntent(
//                    PendingIntentEnum.ACTIVITY,
//                    this,
//                    666,
//                    intent
//                ),
//                triggerAfterSecond = 5,
//                intervalSecond = 5
//            )

        }

        buttonElapsedRealTime.setOnClickListener {

            // one time alarm
//            toggleElapsedRealTimeAlarm(
//                withRepeat = false,
//                withWeakUp = false,
//                pendingIntent = createPendingIntent(
//                    PendingIntentEnum.ACTIVITY,
//                    this,
//                    777,
//                    intent
//                ),
//                triggerAfterSecond = 5,
//            )

        }

        buttonRTCWithRepeat.setOnClickListener {

        }

        buttonRTC.setOnClickListener {

        }
    }

    private fun toggleElapsedRealTimeAlarm(
        withRepeat: Boolean,
        withWeakUp: Boolean,
        pendingIntent: PendingIntent,
        triggerAfterSecond: Int,
        intervalSecond: Int = 60, // default minimum is 60 seconds!!
    ) {
        if (!withRepeat) {

            getAlarmManager(this).set(
                if (withWeakUp) {
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

        getAlarmManager(this).setInexactRepeating(
            if (withWeakUp) {
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


    private fun getAlarmManager(context: Context): AlarmManager {
        return context.getSystemService(ALARM_SERVICE) as AlarmManager
    }

    private fun cancelAlarm(context: Context, pendingIntent: PendingIntent) {
        getAlarmManager(context).cancel(pendingIntent)
    }
}




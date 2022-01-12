package com.swein.androidkotlintool.main.examples.notificationexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.intent.IntentUtility
import com.swein.androidkotlintool.framework.utility.notification.NotificationUtility

class NotificationExampleActivity : AppCompatActivity() {

    private val buttonSimple: Button by lazy {
        findViewById(R.id.buttonSimple)
    }

    private val buttonAction: Button by lazy {
        findViewById(R.id.buttonAction)
    }

    var notificationId = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_notification_example)

        buttonSimple.setOnClickListener {

            val pendingIntent = IntentUtility.createPendingIntent(
                IntentUtility.PendingIntentEnum.ACTIVITY,
                this,
                666,
                intent
            )

            NotificationUtility.showNotification(
                this,
                notificationId,
                NotificationUtility.createSimpleNotification(
                    context = this,
                    title = "a simple notification $notificationId",
                    message = "a simple notification for you",
                    smallIcon = R.drawable.coding_with_cat,
                    channelId = "your_channel_id",
                    channelNameForAndroidO = "your_channel_name",
                    channelDescriptionForAndroidO = "your_channel_description",
                    pendingIntent = pendingIntent,
                    longMessage = "a simple notification for you, subscribe coding with cat is useful, subscribe coding with cat is useful, subscribe coding with cat is useful"
                )
            )

            notificationId++
        }

        buttonAction.setOnClickListener {


        }
    }
}
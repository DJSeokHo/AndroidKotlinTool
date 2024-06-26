package com.swein.androidkotlintool.main.examples.notificationexample

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.intent.IntentUtility
import com.swein.androidkotlintool.framework.utility.notification.NotificationUtility
import com.swein.androidkotlintool.main.examples.broadcastexample.BroadcastReceiverExample

class NotificationExampleActivity : AppCompatActivity() {

    private val broadcastReceiverExample = BroadcastReceiverExample()

    private val buttonSimple: Button by lazy {
        findViewById(R.id.buttonSimple)
    }

    private val buttonBroadcast: Button by lazy {
        findViewById(R.id.buttonBroadcast)
    }

    private val buttonAction: Button by lazy {
        findViewById(R.id.buttonAction)
    }

    private val buttonBigPicture: Button by lazy {
        findViewById(R.id.buttonBigPicture)
    }

    private val buttonFullScreen: Button by lazy {
        findViewById(R.id.buttonFullScreen)
    }

    var notificationId = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_notification_example)


        IntentFilter().apply {
            addAction("com.swein.broadcast.passdata")
            registerReceiver(broadcastReceiverExample, this)
        }


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
                    bigType = NotificationUtility.NotificationBigType.TEXT.apply {
                        any = "a simple notification for you, subscribe coding with cat is useful, subscribe coding with cat is useful, subscribe coding with cat is useful"
                    }
                )
            )

            notificationId++
        }

        buttonBroadcast.setOnClickListener {

            val pendingIntent = IntentUtility.createPendingIntent(
                IntentUtility.PendingIntentEnum.BROADCAST,
                this,
                555,
                Intent().also { intent ->
                    intent.action = "com.swein.broadcast.passdata"
                    intent.putExtra("data", "coding with cat from data")
                    Bundle().also { bundle ->
                        bundle.putString("value1", "coding")
                        bundle.putString("value2", "with")
                        bundle.putString("value3", "cat")

                        intent.putExtra("bundle", bundle)
                    }
                }
            )

            NotificationUtility.showNotification(
                this,
                777,
                NotificationUtility.createSimpleNotification(
                    context = this,
                    title = "a broadcast notification",
                    message = "click this notification to open google map",
                    smallIcon = R.drawable.coding_with_cat,
                    channelId = "your_channel_id",
                    channelNameForAndroidO = "your_channel_name",
                    channelDescriptionForAndroidO = "your_channel_description",
                    pendingIntent = pendingIntent
                )
            )

        }

        buttonAction.setOnClickListener {


        }

        buttonBigPicture.setOnClickListener {

            val pendingIntent = IntentUtility.createPendingIntent(
                IntentUtility.PendingIntentEnum.BROADCAST,
                this,
                888,
                Intent().also { intent ->
                    intent.action = "com.swein.broadcast.passdata"
                    intent.putExtra("data", "coding with cat from data")
                    Bundle().also { bundle ->
                        bundle.putString("value1", "coding")
                        bundle.putString("value2", "with")
                        bundle.putString("value3", "cat")

                        intent.putExtra("bundle", bundle)
                    }
                }
            )

            NotificationUtility.showNotification(
                this,
                777,
                NotificationUtility.createSimpleNotification(
                    context = this,
                    title = "a bit image notification",
                    message = "click this notification to open google map",
                    smallIcon = R.drawable.coding_with_cat,
                    channelId = "your_channel_id",
                    channelNameForAndroidO = "your_channel_name",
                    channelDescriptionForAndroidO = "your_channel_description",
                    pendingIntent = pendingIntent,
                    largeIcon = BitmapFactory.decodeResource(resources, R.drawable.coding_with_cat),
                    bigType = NotificationUtility.NotificationBigType.IMAGE.apply {
                        any = BitmapFactory.decodeResource(resources, R.drawable.coding_with_cat)
                    }
                )
            )
        }

        buttonFullScreen.setOnClickListener {
            // 注意：如果应用的目标平台是 Android 10（API 级别 29）或更高版本，
            // 必须在应用清单文件中请求 USE_FULL_SCREEN_INTENT 权限，以便系统启动与时效性通知关联的全屏 Activity。

            val pendingIntent = IntentUtility.createPendingIntent(
                IntentUtility.PendingIntentEnum.ACTIVITY,
                this,
                567,
                intent
            )

            NotificationUtility.showNotification(
                this,
                999,
                NotificationUtility.createSimpleNotification(
                    context = this,
                    R.drawable.coding_with_cat,
                    title = "Incoming call",
                    message = "123456789",
                    channelId = "your_channel_id",
                    channelNameForAndroidO = "your_channel_name",
                    channelDescriptionForAndroidO = "your_channel_description",
                    pendingIntent = pendingIntent,
                    importanceType = NotificationUtility.NotificationImportanceType.HIGH,
                    category = NotificationCompat.CATEGORY_CALL,
                    isFullScreenIntent = true
                )
            )
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiverExample)
    }
}
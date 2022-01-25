package com.swein.androidkotlintool.main.examples.broadcastexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.notification.NotificationUtility


class BroadcastReceiverExample: BroadcastReceiver() {

    // this callback function is running on main thread
    override fun onReceive(context: Context, intent: Intent) {

        intent.action?.let {
            Log.d("BroadcastReceiverExample", "action $it")
        }

        Log.d("BroadcastReceiverExample", "data ${intent.getStringExtra("data")}")

        intent.getBundleExtra("bundle")?.let {
            Log.d("BroadcastReceiverExample", "${it.getString("value1", "")} ${it.getString("value2", "")} ${it.getString("value3", "")}")
            Toast.makeText(context,
                "${it.getString("value1", "")} ${it.getString("value2", "")} ${it.getString("value3", "")}",
                Toast.LENGTH_SHORT
            ).show()
        }

        Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749,-122.4192?z=11")).apply {
            `package` = "com.google.android.apps.maps"
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(this)
        }

        NotificationUtility.showNotification(
            context,
            777,
            NotificationUtility.createSimpleNotification(
                context = context,
                title = "a broadcast notification",
                message = "just a broadcast notification, not clickable",
                smallIcon = R.drawable.coding_with_cat,
                channelId = "your_channel_id",
                channelNameForAndroidO = "your_channel_name",
                channelDescriptionForAndroidO = "your_channel_description",
                autoCancelAfterSecond = 5
            )
        )
    }
}
package com.swein.androidkotlintool.main.examples.broadcastexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log




class BroadcastReceiverExample: BroadcastReceiver() {

    // run on main thread
    override fun onReceive(context: Context, intent: Intent) {

        intent.action?.let {
            Log.d("BroadcastReceiverExample", "action $it")
        }

        Log.d("BroadcastReceiverExample", "data ${intent.getStringExtra("data")}")

        intent.getBundleExtra("bundle")?.let {
            Log.d("BroadcastReceiverExample", "${it.getString("value1", "")} ${it.getString("value2", "")} ${it.getString("value3", "")}")
        }

        Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749,-122.4192?z=11")).also {
            it.`package` = "com.google.android.apps.maps"
            context.startActivity(it)
        }
    }
}
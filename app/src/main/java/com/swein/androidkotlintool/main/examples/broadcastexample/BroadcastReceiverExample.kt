package com.swein.androidkotlintool.main.examples.broadcastexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.swein.androidkotlintool.main.examples.customintentexample.CustomIntentExampleActivity

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

        Intent(context, CustomIntentExampleActivity::class.java).also {
            context.startActivity(it)
        }
    }
}
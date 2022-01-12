package com.swein.androidkotlintool.main.examples.broadcastexample

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.swein.androidkotlintool.R

class BroadcastExampleActivity : AppCompatActivity() {

    private val broadcastReceiverExample = BroadcastReceiverExample()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_example)

        IntentFilter().apply {
            addAction("com.swein.broadcast.passdata")
            registerReceiver(broadcastReceiverExample, this)
        }


        findViewById<Button>(R.id.button).setOnClickListener {

            Intent().also { intent ->
                intent.action = "com.swein.broadcast.passdata"
                intent.putExtra("data", "coding with cat from data")
                Bundle().also { bundle ->
                    bundle.putString("value1", "coding")
                    bundle.putString("value2", "with")
                    bundle.putString("value3", "cat")

                    intent.putExtra("bundle", bundle)
                }

                sendBroadcast(intent)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiverExample)
    }

}
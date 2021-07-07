package com.swein.androidkotlintool.main.examples.livedata.networkstatemonitor

import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.swein.androidkotlintool.R

class NetworkStateMonitorActivity : AppCompatActivity() {

    private val textViewState: TextView by lazy {
        findViewById(R.id.textViewState)
    }

    private val textViewType: TextView by lazy {
        findViewById(R.id.textViewType)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_state_monitor)

        NetworkStateManager.networkType.observe(this) { type ->
            textViewType.text = type
        }

        NetworkStateManager.networkState.observe(this) { isConnected ->

            textViewState.text = "Network connected?? $isConnected"

            val hint = if (isConnected) {
                "Thumb-up"
            } else {
                "Subscribe"
            }

            Toast.makeText(this, hint, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(NetworkStateManager.networkConnectionReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(NetworkStateManager.networkConnectionReceiver)
    }
}
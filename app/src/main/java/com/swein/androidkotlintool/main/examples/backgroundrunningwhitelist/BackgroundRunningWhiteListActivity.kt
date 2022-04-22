package com.swein.androidkotlintool.main.examples.backgroundrunningwhitelist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Button
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.utility.debug.ILog

class BackgroundRunningWhiteListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_running_white_list)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            requestIgnoreBatteryOptimizations()
        }

        val result = isIgnoringBatteryOptimizations()
        ILog.debug("???", result)

        if (result) {
            button.isEnabled = false
        }
    }

    private fun isIgnoringBatteryOptimizations(): Boolean {

        var isIgnoring = false
        val powerManager = getSystemService(Context.POWER_SERVICE) as? PowerManager

        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(packageName)
        }

        return isIgnoring
    }

    @SuppressLint("BatteryLife")
    private fun requestIgnoreBatteryOptimizations() {
        try {
            Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).also {
                it.data = Uri.parse("package:$packageName")
                startActivity(it)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
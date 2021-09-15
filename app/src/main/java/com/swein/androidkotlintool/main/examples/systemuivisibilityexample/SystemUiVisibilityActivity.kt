package com.swein.androidkotlintool.main.examples.systemuivisibilityexample

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.swein.androidkotlintool.R

class SystemUiVisibilityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_ui_visibility)

//        systemUIVisible(this)
//        systemUIVisible(this, Color.parseColor("#FF5722"))
//        systemUIVisible(this, Color.parseColor("#FF5722"), false)

//        systemUIWithoutStateBarInset(this)
//        systemUIWithoutStateBarInset(this, false)


//        systemUIWithoutStateBar(this)

        fullScreen(this)
    }


    private fun systemUIVisible(activity: Activity, stateBarColor: Int = -1, stateBarTextLight: Boolean = true) {

        val window = activity.window

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        if (stateBarColor != -1) {
            window.statusBarColor = stateBarColor
        }

        val decorView = window.decorView
        decorView.systemUiVisibility = 0
        decorView.systemUiVisibility = if (stateBarTextLight) {
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        else {
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun systemUIWithoutStateBarInset(activity: Activity, stateBarTextLight: Boolean = true) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT

        val decorView = window.decorView
        decorView.systemUiVisibility = 0
        decorView.systemUiVisibility = if (stateBarTextLight) {
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        else {
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun systemUIWithoutStateBar(activity: Activity) {
        val decorView = activity.window.decorView
        decorView.systemUiVisibility = 0
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    private fun fullScreen(activity: Activity) {

        activity.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }

    private fun cancelFullScreen(activity: Activity) {

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

}
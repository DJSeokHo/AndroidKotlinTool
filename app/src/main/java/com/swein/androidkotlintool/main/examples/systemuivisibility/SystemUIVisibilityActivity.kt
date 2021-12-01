package com.swein.androidkotlintool.main.examples.systemuivisibility

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Button
import androidx.core.view.ViewCompat
import com.swein.androidkotlintool.R
import kotlin.random.Random

class SystemUIVisibilityActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_system_uivisibility)

        findViewById<Button>(R.id.buttonVisibleStateBar).setOnClickListener {
            setStateBarVisible(this)
        }
        findViewById<Button>(R.id.buttonInVisibleStateBar).setOnClickListener {
            setStateBarInVisible(this)
        }
        findViewById<Button>(R.id.buttonLightStateBar).setOnClickListener {
            setStateBarToLightTheme(this)
        }
        findViewById<Button>(R.id.buttonDarkStateBar).setOnClickListener {
            setStateBarToDarkTheme(this)
        }
        findViewById<Button>(R.id.buttonStateBarColor).setOnClickListener {

            setStatusBarColor(this, Color.argb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
        }

        findViewById<Button>(R.id.buttonVisibleNavigationBar).setOnClickListener {
            setNavigationBarVisible(this)
        }
        findViewById<Button>(R.id.buttonInVisibleNavigationBar).setOnClickListener {
            setNavigationBarInVisible(this)
        }
        findViewById<Button>(R.id.buttonLightNavigationBar).setOnClickListener {
            setNavigationBarToLightTheme(this)
        }
        findViewById<Button>(R.id.buttonDarkNavigationBar).setOnClickListener {
            setNavigationBarToDarkTheme(this)
        }
        findViewById<Button>(R.id.buttonNavigationBarColor).setOnClickListener {
            setNavigationBarColor(this, Color.argb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
        }



        findViewById<Button>(R.id.buttonEnableImmersive).setOnClickListener {
            enableImmersiveMode(this)
        }
        findViewById<Button>(R.id.buttonDisableImmersive).setOnClickListener {
            disableImmersiveMode(this)
        }


        findViewById<Button>(R.id.buttonLayoutFullScreen).setOnClickListener {
            layoutFullScreen(this)
        }

        findViewById<Button>(R.id.buttonLayoutNormalScreen).setOnClickListener {
            layoutNormalScreen(this)
        }
    }

    fun setStateBarVisible(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.decorView.windowInsetsController?.show(WindowInsets.Type.statusBars())
        }
        else {
            activity.window.decorView.systemUiVisibility = 0
        }
    }

    fun setStateBarInVisible(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
        }
        else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    fun setStateBarToLightTheme(activity: Activity) {
        ViewCompat.getWindowInsetsController(activity.window.decorView)?.isAppearanceLightStatusBars = true
    }

    fun setStateBarToDarkTheme(activity: Activity) {
        ViewCompat.getWindowInsetsController(activity.window.decorView)?.isAppearanceLightStatusBars = false
    }

    fun setStatusBarColor(activity: Activity, color: Int) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    fun setNavigationBarVisible(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.decorView.windowInsetsController?.show(WindowInsets.Type.navigationBars())
        }
        else {
            activity.window.decorView.systemUiVisibility = 0
        }
    }

    fun setNavigationBarInVisible(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.decorView.windowInsetsController?.hide(WindowInsets.Type.navigationBars())
        }
        else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }


    fun setNavigationBarToLightTheme(activity: Activity) {
        ViewCompat.getWindowInsetsController(activity.window.decorView)?.isAppearanceLightNavigationBars = true
    }

    fun setNavigationBarToDarkTheme(activity: Activity) {
        ViewCompat.getWindowInsetsController(activity.window.decorView)?.isAppearanceLightNavigationBars = false
    }

    fun setNavigationBarColor(activity: Activity, color: Int) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.navigationBarColor = color
    }


    fun enableImmersiveMode(activity: Activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.setDecorFitsSystemWindows(false)
            val controller = activity.window.insetsController
            controller?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        else {
            activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }

    }

    fun disableImmersiveMode(activity: Activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.setDecorFitsSystemWindows(true)
            val controller = activity.window.insetsController
            controller?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                controller?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_DEFAULT
            }
        }
        else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

        }
    }

    fun layoutFullScreen(activity: Activity) {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    fun layoutNormalScreen(activity: Activity) {
        activity.window.decorView.systemUiVisibility = 0
    }

}
package com.swein.androidkotlintool.main.examples.systemuivisibility

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import android.widget.Button
import androidx.core.view.ViewCompat
import com.swein.androidkotlintool.R
import com.swein.androidkotlintool.framework.util.log.ILog

class SystemUIVisibilityActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_system_uivisibility)

        findViewById<Button>(R.id.buttonVisibleStateBar).setOnClickListener {

            setSystemStateBarVisible(this)
        }

        findViewById<Button>(R.id.buttonInVisibleStateBar).setOnClickListener {

            setSystemStateBarInVisible(this)
        }



        findViewById<Button>(R.id.buttonLightStateBar).setOnClickListener {

            setSystemStateBarToLightTheme(this)
            setSystemStatusBarColor(this, Color.CYAN)
        }

        findViewById<Button>(R.id.buttonDarkStateBar).setOnClickListener {

            setSystemStateBarToDarkTheme(this)
            setSystemStatusBarColor(this, Color.DKGRAY)
        }

    }


    fun initSystemBars() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            val controller = window.decorView.windowInsetsController

            // 设置状态栏反色
            controller?.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
//            // 取消状态栏反色
//            controller?.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
//            // 设置导航栏反色
//            controller?.setSystemBarsAppearance(APPEARANCE_LIGHT_NAVIGATION_BARS, APPEARANCE_LIGHT_NAVIGATION_BARS)
//            // 取消导航栏反色
//            controller?.setSystemBarsAppearance(0, APPEARANCE_LIGHT_NAVIGATION_BARS)
//            // 同时设置状态栏和导航栏反色
//            controller?.setSystemBarsAppearance((APPEARANCE_LIGHT_STATUS_BARS or APPEARANCE_LIGHT_NAVIGATION_BARS),
//                (APPEARANCE_LIGHT_STATUS_BARS or APPEARANCE_LIGHT_NAVIGATION_BARS))
//            // 同时取消状态栏和导航栏反色
//            controller?.setSystemBarsAppearance(0, (APPEARANCE_LIGHT_STATUS_BARS or APPEARANCE_LIGHT_NAVIGATION_BARS))
//            // 隐藏状态栏
//            controller?.hide(WindowInsets.Type.statusBars())
//            // 显示状态栏
//            controller?.show(WindowInsets.Type.statusBars())
//            // 隐藏导航栏
//            controller?.hide(WindowInsets.Type.navigationBars())
//            // 显示导航栏
//            controller?.show(WindowInsets.Type.navigationBars())
//            // 同时隐藏状态栏和导航栏
//            controller?.hide(WindowInsets.Type.systemBars())
//            // 同时隐藏状态栏和导航栏
//            controller?.show(WindowInsets.Type.systemBars())
        }
    }

    fun setSystemStateBarToLightTheme(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ViewCompat.getWindowInsetsController(window.decorView)?.isAppearanceLightStatusBars = true
        }
        else {
            val lFlags = activity.window.decorView.systemUiVisibility
            activity.window.decorView.systemUiVisibility = lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

    }

    fun setSystemStateBarToDarkTheme(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ViewCompat.getWindowInsetsController(window.decorView)?.isAppearanceLightStatusBars = false
        }
        else {
            val lFlags = activity.window.decorView.systemUiVisibility
            activity.window.decorView.systemUiVisibility = lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }

    }

    fun setSystemStatusBarColor(activity: Activity, color: Int) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }



    fun setSystemStateBarVisible(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.decorView.windowInsetsController?.show(WindowInsets.Type.statusBars())
        }
        else {

        }
    }

    fun setSystemStateBarInVisible(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
        }
        else {

        }
    }

}
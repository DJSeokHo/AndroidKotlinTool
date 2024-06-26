package com.swein.androidkotlintool.framework.utility.window

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.view.ViewCompat

object WindowUtility {


    fun isDarkModeOn(context: Context): Boolean {
        val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    fun fullScreenWithTransparentStatusBar(activity: Activity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    fun transparencyBar(activity: Activity) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ViewCompat.getWindowInsetsController(activity.window.decorView)?.isAppearanceLightStatusBars = true
        }
        else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun setStateBarToDarkTheme(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ViewCompat.getWindowInsetsController(activity.window.decorView)?.isAppearanceLightStatusBars = false
        }
        else {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
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
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
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

    fun toggleFullScreen(activity: Activity) {

        WindowUtility.layoutFullScreen(activity)
        WindowUtility.setStateBarToDarkTheme(activity)
        WindowUtility.setStatusBarColor(activity, Color.TRANSPARENT)
        WindowUtility.setNavigationBarColor(activity, Color.TRANSPARENT)

    }
}
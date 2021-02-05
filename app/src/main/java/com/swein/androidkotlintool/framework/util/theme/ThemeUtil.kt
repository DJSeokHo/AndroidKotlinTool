package com.swein.androidkotlintool.framework.util.theme

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat

class ThemeUtil {
    companion object {
        /**
         * hide system top status bar and bottom navigation button bar
         */
        fun hideSystemUi(activity: Activity) {
            //隐藏虚拟按键
            val decorView = activity.window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }

        /**
         * show system top status bar and bottom navigation button bar
         */
        fun showSystemUi(activity: Activity) {
            //显示虚拟键盘
            val decorView = activity.window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
            decorView.systemUiVisibility = uiOptions
        }

        fun setSystemBottomNavigationBarColor(context: Activity, colorResourceId: Int) {
            try {
                context.window.navigationBarColor = ContextCompat.getColor(context, colorResourceId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * must > API 19
         * put this before setContentView()
         *
         * and add
         * android:fitsSystemWindows="true"
         * to your root layout of xml file
         *
         * @param activity activity
         */
        fun transparencyBar(activity: Activity) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        }

        /**
         * must > API 19
         * put this before setContentView()
         *
         * and add
         * android:fitsSystemWindows="true"
         * to your root layout of xml file
         *
         *
         * @param activity
         * @param colorResId
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun setWindowStatusBarColorResource(activity: Activity, colorResId: Int) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = activity.resources.getColor(colorResId)
        }

        /**
         * must > API 19
         * put this before setContentView()
         *
         * and add
         * android:fitsSystemWindows="true"
         * to your root layout of xml file
         *
         *
         * @param activity
         * @param color
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun setWindowStatusBarColor(activity: Activity, color: Int) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }

        fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean, fullScreen: Boolean) {
            val decor = activity.window.decorView
            if (dark) {
                if (fullScreen) {
                    decor.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    decor.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            } else {
                if (fullScreen) {
                    decor.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                } else {
                    decor.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                }
            }
        }

        fun setSystemBarTheme(pActivity: Activity, pIsDark: Boolean) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val lFlags = pActivity.window.decorView.systemUiVisibility
                pActivity.window.decorView.systemUiVisibility =
                    if (pIsDark) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}
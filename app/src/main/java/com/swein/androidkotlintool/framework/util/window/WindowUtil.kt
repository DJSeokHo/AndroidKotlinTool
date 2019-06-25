package com.swein.androidkotlintool.framework.util.window

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

class WindowUtil {

    companion object {

        /**
         * android full screen above 4.4
         * @param activity activity
         */
        fun fullScreenBackwardCompatibility(activity: Activity) {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                val window = activity.window
                val decorView = window.decorView

                val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                decorView.systemUiVisibility = option
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT

                //                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {

                val window = activity.window
                val attributes = window.attributes
                val flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                val flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                attributes.flags = attributes.flags or flagTranslucentStatus
                //                attributes.flags |= flagTranslucentNavigation;
                window.attributes = attributes

            }
        }

        /**
         *
         * android full screen above 5.0
         *
         * @param activity
         */
        fun fullScreen(activity: Activity) {
            // full screen
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                        or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar

                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
        }

        /**
         *
         * put this before setContentView of Activity
         *
         * without flash actionbar
         *
         * @param activity
         */

        fun hideTitleBarWithFullScreen(activity: Activity) {
            // hide title bar
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
            // hide state bar
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

    }

}